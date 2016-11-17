package views.support

import conf.Static

case class FiveADayItem(imageSrc: String, articleUrl: String, kicker: String, standfirst: String)

object FiveADayHelpers {
  def getItems: Seq[FiveADayItem] = Seq(
    FiveADayItem(
      Static("images/five/person-one/sidebar_number_1.png"),
      "/film/2016/sep/01/arrival-review-amy-adams-denis-villeneuve-alien-contact",
      "Arrival Review",
      "Amy Adams has a sublime word with alien visitors"
    ),
    FiveADayItem(
      Static("images/five/person-one/sidebar_number_2.png"),
      "/lifeandstyle/2016/nov/16/fish-fingers-taste-test-which-get-the-thumbs-up",
      "Taste tests",
      "Which fish fingers gets the thumbs up?"
    ),
    FiveADayItem(
      Static("images/five/person-one/sidebar_number_1.png"),
      "/film/2016/sep/01/arrival-review-amy-adams-denis-villeneuve-alien-contact",
      "Arrival Review",
      "Amy Adams has a sublime word with alien visitors"
    ),
    FiveADayItem(
      Static("images/five/person-one/sidebar_number_1.png"),
      "/film/2016/sep/01/arrival-review-amy-adams-denis-villeneuve-alien-contact",
      "Arrival Review",
      "Amy Adams has a sublime word with alien visitors"
    ),
    FiveADayItem(
      Static("images/five/person-one/sidebar_number_1.png"),
      "/film/2016/sep/01/arrival-review-amy-adams-denis-villeneuve-alien-contact",
      "Arrival Review",
      "Amy Adams has a sublime word with alien visitors"
    )
  )
}
