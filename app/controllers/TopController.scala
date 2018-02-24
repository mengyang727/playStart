package controllers

import java.security.MessageDigest
import javax.inject._

import dao.UserDao
import org.apache.commons.codec.binary.Hex

import scala.util.Success

//import play.twirl.api.Html

//import play.filters.csrf._
//import play.api.mvc._
import play.api.mvc._

@Singleton
//@AddCSRFToken
class TopController @Inject()(cc: ControllerComponents, userDao: UserDao) extends AbstractController(cc) {
  def top() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.top(LoginModel.form)).withNewSession
  }

//  def toTest(p001UserMailAddress: String, p001UserPassword: String) = Action { implicit request: Request[AnyContent] =>
//    Ok("toTest(p001UserMailAddress: String, p001UserPassword: String)" + p001UserMailAddress + " & " + p001UserPassword)
//  }

  def login() = Action { implicit request =>
//    val loginForm = Form(
//      tuple(
//        "p001UserMailAddress" -> email,
//        "p001UserPassword" -> text(minLength = 6)
//      )
//    )

    val loginForm = LoginModel.form

    loginForm.bindFromRequest().fold(
      errorForm => Ok(errorForm.errors.toString()),
//      formWithErrors => BadRequest(view.html.top(formWithErrors)),
      formData => {
//        Logger.info(formData.userMailAddress)
        userDao.findByMailIgnoreCase(formData.userMailAddress) match {
          case Success(Some(user)) => {
            if (getSha(formData.userPassword) == user.secPassword) {
              Redirect(routes.OptionHistoryController.optionHistory()).withSession(("userId", user.id.toString))
            } else {
              Ok("wrong")
            }
          }
        }
//        val (p001UserMailAddress, p001UserPassword) = formData
//        Ok("toTest()" + p001UserMailAddress + "&" + p001UserPassword)
//        Ok(Html("<h1>toTest()" + formData.userMailAddress + "&" + formData.userPassword + "</h1>"))
//        Redirect(routes.TestController.test())
//        Ok(views.html.optionHistory())

      }
    )
  }

  private final lazy val md = MessageDigest.getInstance("SHA-512")

  private def getSha(s: String): String = {
    new String(Hex.encodeHex(md.digest(s.getBytes)))
  }
}
