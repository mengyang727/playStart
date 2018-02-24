package dao

import jp.microad.c3po.persistence.jdbc.dao.{JdbcDao, RecordsConverter}
import jp.microad.c3po.persistence.jdbc.{ClientPool, Records}
import play.api.libs.json.Json

import scala.util.Try

case class Account(id: Int, name: String)

object Account {
  implicit val accountFormat = Json.format[Account]
}

class AccountDao(override val pool: ClientPool) extends JdbcDao {

  private final implicit val accountConverter = new RecordsConverter[Seq[Account]] {
    override def apply(records: Records): Seq[Account] = {
      (records map { record =>
        Account(record.int("co_account_id"), new String(record.bytes("co_account_name"), "UTF-8"))
      }).toSeq
    }
  }

  def findAll: Try[Seq[Account]] = {
    val sql =
      s"""
        |SELECT co_account_id, co_account_name FROM co_account_master LIMIT 10
        """.stripMargin
      findAll(sql)
  }
}
