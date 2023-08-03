package org.redata.ddl.definition

import org.intellij.lang.annotations.Language

data class TableDefinition(
    val title: String,
    val columns: List<ColumnDefinition>
) {
    @Language("SQL")
    fun toSelectQuery() =
        "SELECT * FROM $title"
}