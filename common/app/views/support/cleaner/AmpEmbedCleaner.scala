package views.support.cleaner

import model.{Article, VideoAsset}
import org.jsoup.nodes.{Document, Element}
import views.support.{AmpSrcCleaner, HtmlCleaner}
import conf.switches.Switches.AmpInteractivePlaceHolderAttribute

import scala.collection.JavaConversions._

case class AmpEmbedCleaner(article: Article) extends HtmlCleaner {

  def cleanAmpVideos(document: Document): Unit = {
    document.getElementsByTag("video").foreach(video => {
      val posterSrc = video.attr("poster")
      val newPosterSrc = AmpSrcCleaner(posterSrc).toString
      val mediaId = video.attr("data-media-id")
      val fallback = "<div fallback > Sorry, your browser is unable to play this video.<br/> Please <a href='http://whatbrowser.org/'>upgrade</a> to a modern browser and try again.</div>"

      video.tagName("amp-video")
      video.removeAttr("data-media-id")
      video.getElementsByTag("source").remove()
      video.append(fallback)
      video.attr("poster", newPosterSrc)
      // Need to hard code aspect ratio 5:3 for Amp pages.
      video.attr("width", "5")
      video.attr("height", "3")
      // Layout responsive keeps the aspect ratio, but ignores the height and width attributes above
      video.attr("layout", "responsive")
      video.attr("controls", "")

      val sourceHTML: String = getVideoAssets(mediaId).map { videoAsset =>
        videoAsset.encoding.map { encoding =>
          if (encoding.url.startsWith("https")) {
            s"""<source src="${encoding.url}" type="${encoding.format}"></source>"""
          }
        }.getOrElse("")
      }.mkString("")

      video.append(sourceHTML)
    })
  }

  sealed abstract class AmpExternalVideo(val videoId: String, val elementType: String)
  case class YoutubeExternalVideo(override val videoId: String) extends AmpExternalVideo(videoId, "amp-youtube")
  case class VimeoExternalVideo(override val videoId: String) extends AmpExternalVideo(videoId, "amp-vimeo")

  object AmpExternalVideo {
    def getAmpExternalVideoByUrl(videoUrl: String) : Option[AmpExternalVideo] = {
      val youtubePattern = """^https?:\/\/www\.youtube\.com\/watch\?v=([^#&?]+).*""".r
      val vimeoPattern = """^https?:\/\/vimeo\.com\/(\d+).*""".r
      videoUrl match {
        case youtubePattern(videoId) => Some(YoutubeExternalVideo(videoId))
        case vimeoPattern(videoId) => Some(VimeoExternalVideo(videoId))
        case _ => None
      }
    }

    def createElement(document: Document, videoId: String, elementType: String): Element = {
      val video = document.createElement(elementType)
      video.attr("data-videoid", videoId)
      video.attr("width", "5")
      video.attr("height", "3")
      video.attr("layout", "responsive")
    }

    def clean(document: Document) = {
      for {
        videoElement <- document.getElementsByClass("element-video")
        iframeElement <- videoElement.getElementsByTag("iframe")
        ampExternalVideo <- getAmpExternalVideoByUrl(videoElement.attr("data-canonical-url"))
      } yield {
        val ampVideoElement = createElement(document, ampExternalVideo.videoId, ampExternalVideo.elementType)
        videoElement.appendChild(ampVideoElement)
        iframeElement.remove()
      }
    }
  }

  object AmpSoundcloud {
    def createElement(document: Document, trackId: String): Element = {
      val soundcloud = document.createElement("amp-soundcloud")
      soundcloud.attr("data-trackid", trackId)
      soundcloud.attr("data-visual", "true")
      soundcloud.attr("height", "300") // height is necessary if data-visual == true
    }

    def getTrackIdFromUrl(soundcloudUrl: String): Option[String] = {
      val pattern = ".*soundcloud.com%2Ftracks%2F(\\d+).*".r
      soundcloudUrl match {
        case pattern(trackId) => Some(trackId)
        case _ => None
      }
    }

    def clean(document: Document) = {
      for {
        audioElement <- document.getElementsByClass("element-audio")
        iframeElement <- audioElement.getElementsByTag("iframe")
        trackId <- getTrackIdFromUrl(iframeElement.attr("src"))
      } yield {
        val soundcloudElement = createElement(document, trackId)
        audioElement.appendChild(soundcloudElement)
        iframeElement.remove()
      }
    }
  }

  def cleanAmpEmbed(document: Document) = {
    document.getElementsByClass("element-embed")
      .filter(_.getElementsByTag("iframe").length != 0)
      .foreach(_.getElementsByTag("iframe").map(_.remove))
  }

  def cleanAmpInstagram(document: Document) = {
    document.getElementsByClass("element-instagram").foreach { embed: Element =>
      embed.getElementsByTag("a").map { element: Element =>
        val src = element.attr("href")
        val list = src.split("""instagram\.com/p/""")
        (if (list.length == 1) None else list.lastOption).flatMap(_.split("/").headOption).map { shortcode =>
          val instagram = document.createElement("amp-instagram")

          instagram
            .attr("data-shortcode", shortcode)
            .attr("width", "7") // 8:7 seems to be the normal ratio
            .attr("height", "8")
            .attr("layout", "responsive")

          embed
            .empty()
            .appendChild(instagram)
        }
      }
    }

  }

  def cleanAmpInteractives(document: Document) = {

    document.getElementsByClass("element-interactive").filter { element: Element =>
      element.getElementsByTag("a").length !=0
    }.foreach { interactive: Element =>
      val link = interactive.getElementsByTag("a")
      val linkToInteractive = link.first().attr("href")
      val iframe = document.createElement("amp-iframe")
      val overflowElem = document.createElement("div")
      // In AMP, when using the layout `responsive`, width is 100%,
      // and height is decided by the ratio between width and height.
      // https://www.ampproject.org/docs/guides/responsive/control_layout.html
      iframe.attr("width", "5")
      iframe.attr("height", "1")
      iframe.attr("layout", "responsive")
      iframe.attr("resizable", "")
      iframe.attr("sandbox", "allow-scripts allow-same-origin")
      iframe.attr("src", linkToInteractive)

      // All interactives should resize to the correct height once they load,
      // but if they don't this overflow element will show and load it fully once it is clicked
      overflowElem.addClass("cta cta--medium cta--show-more cta--show-more__unindent")
      overflowElem.text("See the full visual")
      overflowElem.attr("overflow", "")
      if(AmpInteractivePlaceHolderAttribute.isSwitchedOn) {
        overflowElem.attr("placeholder", "")
      }
      link.remove()
      iframe.appendChild(overflowElem)
      interactive.appendChild(iframe)
    }
  }

  def cleanAmpMaps(document: Document) = {
    document.getElementsByClass("element-map").foreach { embed: Element =>
      embed.getElementsByTag("iframe").foreach { element: Element =>
        val src = element.attr("src")
        val frameBorder = element.attr("frameborder")
        val elementMap = document.createElement("amp-iframe")

        // In AMP, when using the layout `responsive`, width is 100%,
        // and height is decided by the ratio between width and height.
        // https://www.ampproject.org/docs/guides/responsive/control_layout.html
        val attrs = Map(
        "width" -> "4",
        "height" -> "3",
        "layout" -> "responsive",
        "sandbox" -> "allow-scripts allow-same-origin allow-popups",
        "frameborder" -> frameBorder,
        "src" -> src
        )
        attrs.foreach {
          case (key, value) => elementMap.attr(key, value)
        }
        // The following replaces the iframe element with the newly created amp-iframe element
        // with the figure element as its parent.
        element
        .replaceWith(elementMap)
      }
    }
  }

  private def getVideoAssets(id:String): Seq[VideoAsset] = article.elements.bodyVideos.filter(_.properties.id == id).flatMap(_.videos.videoAssets)

  override def clean(document: Document): Document = {

    cleanAmpVideos(document)
    AmpExternalVideo.clean(document)
    AmpSoundcloud.clean(document)
    cleanAmpMaps(document)
    cleanAmpInstagram(document)
    cleanAmpInteractives(document)
    cleanAmpEmbed(document)

    document
  }

}
