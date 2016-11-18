package views.support

import conf.Static

case class FiveADayItem(imageSrc: String, articleUrl: String, kicker: String, standfirst: String)

object FiveADayHelpers {
  def getItems: Seq[FiveADayItem] = Seq(
    FiveADayItem(
      Static("images/five/nicholas-fonsing/sidebar_number_1.png"),
      "/film/2016/sep/01/arrival-review-amy-adams-denis-villeneuve-alien-contact",
      "Arrival Review",
      "Amy Adams has a sublime word with alien visitors"
    ),
    FiveADayItem(
      Static("images/five/nicholas-fonsing/sidebar_number_2.png"),
      "/lifeandstyle/2016/nov/16/fish-fingers-taste-test-which-get-the-thumbs-up",
      "Taste tests",
      "Which fish fingers gets the thumbs up?"
    ),
    FiveADayItem(
      Static("images/five/nicholas-fonsing/sidebar_number_3.png"),
      "/technology/2016/nov/17/elon-musk-satellites-internet-spacex",
      "Elon Musk",
      "wants to cover the world wth internet from space"
    ),
    FiveADayItem(
      Static("images/five/nicholas-fonsing/sidebar_number_4.png"),
      "/us-news/2016/nov/17/hillary-clinton-first-public-appearance-bernie-sanders",
      "Clinton’",
      "'There have been times when I wanted never to leave the house again'"
    ),
    FiveADayItem(
      Static("images/five/nicholas-fonsing/sidebar_number_5.png"),
      "/technology/shortcuts/2016/nov/14/mobile-smartphones-health-silly-walk",
      "The 'phone walk'",
      "the next stage in human evolution"
    )
  )
}
