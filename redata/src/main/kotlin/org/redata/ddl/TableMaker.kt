package org.redata.ddl

import org.intellij.lang.annotations.Language
import org.redata.ddl.definition.TableDefinition
import java.sql.Connection

object TableMaker {
    fun make(connection: Connection, tableDefinition: TableDefinition) {
        val statement = connection.createStatement()
        val columnsPart = tableDefinition.columns.joinToString(", ") { "${it.title} ${it.type}" }
        @Language("SQL") val insertQuery = "CREATE TABLE ${tableDefinition.title}($columnsPart)"
        statement.executeUpdate(insertQuery)
    }
}