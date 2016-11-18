package views.support

import conf.Static

case class FiveADayItem(imageSrc: String, articleUrl: String, kicker: String, standfirst: String)

object FiveADayHelpers {
  def getItems: Seq[FiveADayItem] = Seq(
    FiveADayItem(
      Static("images/five/daniel-arwas/sidebar_number_1.png"),
      "/film/2016/sep/01/arrival-review-amy-adams-denis-villeneuve-alien-contact",
      "Arrival Review",
      "Amy Adams has a sublime word with alien visitors"
    ),
    FiveADayItem(
      Static("images/five/daniel-arwas/sidebar_number_2.png"),
      "/lifeandstyle/2016/nov/16/fish-fingers-taste-test-which-get-the-thumbs-up",
      "Taste tests",
      "Which fish fingers gets the thumbs up?"
    ),
    FiveADayItem(
      Static("images/five/daniel-arwas/sidebar_number_3.png"),
      "/technology/2016/nov/17/elon-musk-satellites-internet-spacex",
      "Elon Musk",
      "wants to cover the world wth internet from space"
    ),
    FiveADayItem(
      Static("images/five/daniel-arwas/sidebar_number_4.png"),
      "/football/blog/2016/nov/18/premier-league-10-things-to-look-out-for-this-weekend",
      "Premier League’",
      "10 things to look out for this weekend"
    ),
    FiveADayItem(
      Static("images/five/daniel-arwas/sidebar_number_5.png"),
      "/technology/shortcuts/2016/nov/14/mobile-smartphones-health-silly-walk",
      "The 'phone walk'",
      "the next stage in human evolution"
    )
  )
}
