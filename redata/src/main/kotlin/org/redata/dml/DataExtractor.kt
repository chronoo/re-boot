package org.redata.dml

import org.redata.ddl.definition.TableDefinition
import org.redata.TestEntity
import java.sql.Connection

object DataExtractor {
    fun extract(connection: Connection, tableDefinition: TableDefinition): List<TestEntity> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(tableDefinition.toSelectQuery())
        val result = mutableListOf<TestEntity>()
        while (resultSet.next()) {
            val name = resultSet.getString(1)
            val age = resultSet.getInt(2)
            result.add(TestEntity(name, age))
        }
        return result.toList()
    }
}