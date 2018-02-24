package dao

import java.util.Date

import jp.microad.c3po.persistence.jdbc.dao.{JdbcDao, RecordsConverter}
import jp.microad.c3po.persistence.jdbc.{ClientPool, Records}
import play.Logger
import play.api.libs.json.Json

import scala.util.Try

case class DispOperationHistory(
                                 historyId: String,
                                 createTime: String,
                                 userName: String,
                                 coAccountIds: String,
                                 urlGroupIds: String,
                                 targetingType: String,
                                 structIds: String,
                                 platform: String,
                                 targetUnit: String,
                                 entryTypeName: String,
                                 actionType: String,
                                 registMethod: String,
                                 uploadFileName: String,
                                 confirmFileName: String
                               )

object DispOperationHistory {
  implicit val dispOperationHistoryFormat = Json.format[DispOperationHistory]
}

class OperationHistoryDao(override val pool: ClientPool) extends JdbcDao {

  private final implicit val operationHistoryConverter = new RecordsConverter[Seq[DispOperationHistory]] {
    override def apply(records: Records): Seq[DispOperationHistory] = {
      (records map { r =>
        DispOperationHistory(
          r.int("history_id").toString(),
          r.string("create_time"),
          r.int("user_id").toString(),
          new String(r.bytes("co_account_ids")),
//          new String(r.bytes("urlgroup_ids")),
          new String(),
//          new String(r.bytes("targeting_type")),
          new String(),
//          new String(r.bytes("struct_ids")),
          new String(),
//          r.int("platform").toString(),
          new String(),
          r.string("target_unit"),
          r.string("entry_type_name"),
//          r.string("action_type"),
          new String(),
//          r.string("regist_method"),
          new String(),
//          new String(r.bytes("upload_file_name")),
          new String(),
          new String(r.bytes("confirm_file_name")))
      }).toSeq
    }
  }

  def selectOperationHistoryList(startDate: Date, endDate: Date): Try[Seq[DispOperationHistory]] = {
    val sql =
      s"""
         |SELECT
         |    oh.history_id,
         |    oh.user_id,
         |    oh.co_account_ids,
         |    oh.urlgroup_ids,
         |    oh.targeting_type,
         |    oh.struct_ids,
         |    oh.platform,
         |    oh.target_unit,
         |    etm.entry_type_name,
         |    oh.action_type,
         |    oh.entry_type_id,
         |    oh.regist_method,
         |    oh.upload_file_name,
         |    oh.confirm_file_name,
         |    oh.create_time
         |FROM
         |    operation_history oh
         |INNER JOIN
         |    entry_type_master etm
         |ON
         |    oh.entry_type_id = etm.entry_type_id
         |WHERE
         |    DATE_FORMAT(oh.create_time, '%Y%m%d') >= DATE_FORMAT( ?, '%Y%m%d')
         |AND
         |    DATE_FORMAT(oh.create_time, '%Y%m%d') <= DATE_FORMAT( ?, '%Y%m%d')
         |ORDER BY history_id DESC
        """.stripMargin
    Logger.info(sql)
    findAll(sql, startDate, endDate)
//    findAll(sql)
  }

}
