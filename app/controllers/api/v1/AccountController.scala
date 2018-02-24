package controllers.api.v1

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import dao.AccountDao

import scala.util.{Failure, Success}

class AccountController @Inject()(
   cc: ControllerComponents,
   accountDao: AccountDao) extends AbstractController(cc) {

  def list = Action {

    accountDao.findAll match {
      case Success(accounts) => {
        Ok(Json.toJson(accounts))
      }
      case Failure(e) => {
        Ok(e.toString())
      }
    }
  }
}
