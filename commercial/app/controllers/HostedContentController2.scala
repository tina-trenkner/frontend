package commercial.controllers

import common.{Edition, ExecutionContexts, Logging}
import contentapi.ContentApiClient
import model.Cached.RevalidatableResult
import model.{Cached, NoCache}
import play.api.mvc._

import scala.util.control.NonFatal

class HostedContentController2(contentApiClient: ContentApiClient)
  extends Controller with ExecutionContexts with Logging with implicits.Requests {

  def renderHostedPage(itemId: String) = Action.async { implicit request =>

    val eventualResponse = contentApiClient.getResponse(
      contentApiClient.item(itemId, Edition(request))
      .showSection(true)
      .showElements("all")
      .showFields("all")
      .showTags("all")
      .showAtoms("all")
    )

    eventualResponse.onFailure {
      case NonFatal(e) => log.warn(s"Capi lookup of item '$itemId' failed", e)
    }

    eventualResponse map { response =>
      response.content map { item =>
      //  Cached(60)(RevalidatableResult.Ok(views.html.hosted.hostedPage(item)))

        println(item.id)
        println(item.section)

        Ok(item.toString())
      } getOrElse {
        NoCache(NotFound)
      }
    }
  }
}
