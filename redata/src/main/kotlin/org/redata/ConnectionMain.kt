package org.redata

import org.redata.connection.ConnectionManager.connection
import org.redata.ddl.TableMaker
import org.redata.ddl.definition.ColumnDefinition
import org.redata.ddl.definition.TableDefinition
import org.redata.dml.DataExtractor
import org.redata.dml.DataInserter

fun main() {
    val tableDefinition = TableDefinition("TEST", listOf(
        ColumnDefinition("NAME", "varchar"),
        ColumnDefinition("AGE", "number"),
    ))
    val test1 = TestEntity("My", 123)
    val test2 = TestEntity("Your", 42)

    connection.use { connection ->
        TableMaker.make(connection, tableDefinition)
        DataInserter.insertAll(connection, listOf(test1, test2))
        DataExtractor.extract(connection, tableDefinition)
            .forEach(::println)
    }
}

