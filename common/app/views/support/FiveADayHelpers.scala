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
      Static("images/five/person-one/sidebar_number_3.png"),
      "/music/2016/nov/11/the-future-50-the-rising-stars-to-look-out-for",
      "The future 50",
      "The rising music stars to look out for"
    ),
    FiveADayItem(
      Static("images/five/person-one/sidebar_number_4.png"),
      "/technology/shortcuts/2016/nov/14/mobile-smartphones-health-silly-walk",
      "The ‘phone walk’",
      "The next stage in human evolution"
    ),
    FiveADayItem(
      Static("images/five/person-one/sidebar_number_5.png"),
      "/us-news/2016/nov/16/facebook-bias-bubble-us-election-conservative-liberal-news-feed",
      "Bursting the Facebook bubble",
      "What happens when voters on the left and right swap feeds?"
    )
  )
}
