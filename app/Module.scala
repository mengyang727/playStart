import com.google.inject.AbstractModule
import dao.{AccountDao, OperationHistoryDao, UserCoAccountDao, UserDao}
import jp.microad.c3po.config.Config
import jp.microad.c3po.persistence.jdbc.ClientPool
import jp.microad.c3po.pool.Source

class Module extends AbstractModule {
  override def configure() = {

    val corePool = ClientPool(
      new Source {override val name="core_master_db"},
      Config("core_master_db"),
      callbackOnSupervisorError
    )
    bind(classOf[AccountDao]).toInstance(new AccountDao(corePool))
    bind(classOf[UserDao]).toInstance(new UserDao(corePool))
    bind(classOf[UserCoAccountDao]).toInstance(new UserCoAccountDao(corePool))

    val ds_SspPubSiteDb = ClientPool(
      new Source {override val name="ssp_pub_site_db"},
      Config("ssp_pub_site_db"),
      callbackOnSupervisorError
    )
    bind(classOf[OperationHistoryDao]).toInstance(new OperationHistoryDao(ds_SspPubSiteDb))
  }

  private def callbackOnSupervisorError: (Exception) => Unit = (e) => {
    println(s"Fail to supervise. ${e.getMessage}")
  }
}
