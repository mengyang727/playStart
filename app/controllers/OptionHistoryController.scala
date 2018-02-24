package controllers

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.{Inject, Singleton}

import dao.{OperationHistoryDao, UserCoAccountDao}
import play.api.libs.json.Json
import play.api.mvc._

import scala.util.{Failure, Success}

@Singleton
class OptionHistoryController @Inject()(cc: ControllerComponents, userCoAccountDao: UserCoAccountDao, operationHistoryDao: OperationHistoryDao) extends AbstractController(cc) {
  def optionHistory() = Action { implicit request: Request[AnyContent] =>
//    val userId = request.session.get("userId")
//    userCoAccountDao.selectAllUserCoAccount() match {
//      case Success(dispAccount) => {
//        Ok(Json.toJson(dispAccount))
//      }
//    }
    Ok(views.html.optionHistory())
  }

  def getOptionHistoryList() = Action { implicit request: Request[AnyContent] =>
//    val userId = request.session.get("userId")
//    val cal = Calendar.getInstance()
//    cal.add(Calendar.DATE, -7)
//    val startDate = cal.getTime()
//    val endDate = new Date()
    val df = new SimpleDateFormat("yyyy-MM-dd")
    val startDate = df.parse("2018-02-01")
    val endDate = new Date()
    operationHistoryDao.selectOperationHistoryList(startDate, endDate) match {
      case Success(dispOperationHistory) => {
        Ok(Json.toJson(dispOperationHistory))
      }
      case Failure(e) => {
        Ok(e.toString())
      }
    }
  }
}
