package net.corda.behave.database.configuration

import net.corda.behave.database.DatabaseConfigurationTemplate
import net.corda.behave.node.configuration.DatabaseConfiguration

class OracleConfigurationTemplate : DatabaseConfigurationTemplate() {

    override val connectionString: (DatabaseConfiguration) -> String
        get() = { "jdbc:oracle:thin:@${it.host}:${it.port}:xe" }

    override val config: (DatabaseConfiguration) -> String
        get() = {
            """
            |dataSourceProperties = {
            |    dataSourceClassName = "oracle.jdbc.pool.OracleDataSource"
            |    dataSource.url = "${connectionString(it)}"
            |    dataSource.user = "${it.username}"
            |    dataSource.password = "${it.password}"
            |}
            |database = {
            |    runMigration=true
            |    transactionIsolationLevel = READ_COMMITTED
            |    schema="${it.schema}"
            |}
            """
        }
}