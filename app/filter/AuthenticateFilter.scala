package filter

import play.api.mvc.{EssentialAction, EssentialFilter, RequestHeader, Results}
import controllers.routes

class AuthenticateFilter extends EssentialFilter {

  def apply(next: EssentialAction) = (requestHeader: RequestHeader) => {
    if (requestHeader.uri == routes.TopController.top().url
      | requestHeader.uri == routes.TopController.login().url
      | requestHeader.session.get("userId").isDefined) {
      next(requestHeader)
    } else {
      play.api.libs.streams.Accumulator.done(
        Results.Redirect(routes.TopController.top())
      )
    }
  }
}
