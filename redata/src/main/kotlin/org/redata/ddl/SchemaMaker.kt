package org.redata.ddl

import org.redata.ddl.definition.TableDefinition
import java.sql.Connection

object SchemaMaker {
    fun init(connection: Connection, tableDefinitions: List<TableDefinition>) {
        for (tableDefinition in tableDefinitions) {
            TableMaker.make(connection, tableDefinition)
        }
    }
}

object TableDefinitionLocator {
    fun locate(): List<TableDefinition> {
        return listOf()
    }
}

