package org.redata.ddl

import org.rebase.ClassLocator
import java.sql.Connection

object DBInitializer {
    fun init(connection: Connection) {
        val allClasses = ClassLocator.findAllClasses()
        val tableDefinitions = TableDefinitionLocator.locate(allClasses)
        SchemaMaker.init(connection, tableDefinitions)
    }
}
