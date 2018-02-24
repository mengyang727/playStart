package dao

import jp.microad.c3po.persistence.jdbc.dao.{JdbcDao, RecordsConverter}
import jp.microad.c3po.persistence.jdbc.{ClientPool, Records}
import play.Logger
import play.api.libs.json.Json

import scala.util.Try

case class DispAccount(accountId: Int, accountName: String)

object DispAccount {
  implicit val dispAccountFormat = Json.format[DispAccount]
}

class UserCoAccountDao(override val pool: ClientPool) extends JdbcDao {

  private final implicit val dispAccountConverter = new RecordsConverter[Seq[DispAccount]] {
    override def apply(records: Records): Seq[DispAccount] = {
      (records map { r =>
        DispAccount(
          r.int("co_account_id"),
//          r.string("co_account_name"))
          new String(r.bytes("co_account_name"), "UTF-8"))
      }).toSeq
    }
  }

  def selectAllUserCoAccount(): Try[Seq[DispAccount]] = {
    val sql =
      s"""
         |SELECT
         |    DISTINCT cam.co_account_id,
         |    cam.co_account_name
         |FROM
         |    co_account_master cam
         |WHERE
         |    cam.soft_delete_flag = 'open'
         |AND
         |    cam.product_id = 1
         |ORDER BY
         |    cam.co_account_id ASC
        """.stripMargin
    Logger.info(sql)
    findAll(sql)
  }
}
