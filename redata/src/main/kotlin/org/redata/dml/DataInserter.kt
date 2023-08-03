package org.redata.dml

import org.redata.dml.entity.InsertableEntity
import java.sql.Connection

object DataInserter {
    fun insert(connection: Connection, entity: InsertableEntity) {
        val statement = connection.createStatement()
        statement.executeUpdate(entity.toInsertSql())
    }

    fun insertAll(connection: Connection, entity: Collection<InsertableEntity>) {
        entity.forEach { insert(connection, it) }
    }
}