package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.mvc._

class ApplicationController extends Controller {
  def login = Action {
    Ok(views.html.login("用户登录"))
  }

//  def doLogin(userName: String, password: String) = Action {
//    val mess = userName + "&" + password
//    Ok(mess)
//  }

  def doLogin = Action { implicit request =>
    val loginForm = Form(
      tuple(
        "userName" -> email,
        "password" -> text(minLength = 6)
      )
    )

    loginForm.bindFromRequest().fold(
      errorForm => Ok(errorForm.errors.toString()),
      tupleData => {
        val (userName, password) = tupleData
        Ok(userName + "&" + password)
      }
    )
  }

}