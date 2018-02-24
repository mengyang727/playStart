package controllers

object LoginModel {
  import play.api.data.Form
  import play.api.data.Forms._

  case class Data(userMailAddress: String, userPassword: String)

  val form = Form(
    mapping(
      "userMailAddress" -> email,
//      "userPassword" -> nonEmptyText
      "userPassword" -> text(minLength = 6)
    )(Data.apply)(Data.unapply)
  )
}
