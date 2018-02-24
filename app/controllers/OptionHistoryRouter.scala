package controllers

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class OptionHistoryRouter@Inject()(controller: OptionHistoryController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/") => controller.getOptionHistoryList
  }
}
