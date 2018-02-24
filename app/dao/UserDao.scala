package dao

import jp.microad.c3po.persistence.jdbc.dao.{JdbcDao, RecordOptConverter}
import jp.microad.c3po.persistence.jdbc.{ClientPool, Record}
import play.Logger

import scala.util.Try

sealed abstract class UserClass(val name: String)


object UserClass {

  case object CoAccount extends UserClass("co_account")

  case object Master extends UserClass("master")

  val values = Seq(CoAccount, Master)

  def apply(name: String): Option[UserClass] = values.find(_.name == name)

}
case class User(id: Int, userClass: Option[UserClass], secMailAddress: String, secPassword: String, secName: String)

class UserDao(override val pool: ClientPool) extends JdbcDao {

  private final implicit val userConverter = new RecordOptConverter[Option[User]] {
    override def apply(record: Option[Record]): Option[User] = {
      record map { r =>
        User(
          r.int("user_id"),
          UserClass.apply(r.string("user_class")),
          r.string("sec_user_mail_address"),
          r.string("sec_user_password"),
          r.string("sec_user_name"))
      }
    }
  }

  def findByMailIgnoreCase(secMailAddress: String): Try[Option[User]] = {
    val sql =
      s"""
        |SELECT user_id, user_class, sec_user_mail_address, sec_user_password, sec_user_name
        | FROM user_master WHERE lower(CAST(sec_user_mail_address AS CHAR)) = ? LIMIT 1
        """.stripMargin
      Logger.info(sql)
      find(sql, secMailAddress)
  }
}
