package org.redata

import org.redata.connection.ConnectionManager.connection
import org.redata.ddl.TableMaker
import org.redata.ddl.definition.ColumnDefinition
import org.redata.ddl.definition.TableDefinition
import org.redata.dml.DataExtractor
import org.redata.dml.DataInserter
import org.redata.tcl.TransactionManager

fun main() {
    val tableDefinition = TableDefinition(
        "TEST", listOf(
            ColumnDefinition("NAME", "varchar"),
            ColumnDefinition("AGE", "number"),
        )
    )
    connection.use { connection ->
        val entities = TransactionManager.runInTransaction(connection) {
            TableMaker.make(connection, tableDefinition)
            DataInserter.insertAll(
                connection,
                listOf(
                    TestEntity("My", 123),
                    TestEntity("Your", 42)
                )
            )
            DataExtractor.extract(connection, tableDefinition)
                .forEach(::println)

            println("make savepoint")
            val savepoint = connection.setSavepoint()
            DataInserter.insertAll(
                connection, listOf(
                    TestEntity("test1", 2),
                    TestEntity("test2", 3),
                )
            )

            DataExtractor.extract(connection, tableDefinition)
                .forEach(::println)
            connection.rollback(savepoint)

            println("rollback savepoint")
            DataExtractor.extract(connection, tableDefinition)
                .onEach(::println)
        }
        entities.forEach { println(it) }
    }
}
