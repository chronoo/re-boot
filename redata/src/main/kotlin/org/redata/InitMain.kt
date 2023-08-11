package org.redata

import org.redata.connection.ConnectionManager
import org.redata.ddl.DBInitializer
import org.redata.ddl.definition.Column
import org.redata.ddl.definition.ReEntity

fun main() {
    ConnectionManager.connection.use { connection ->
        DBInitializer.init(connection)
    }
}

@ReEntity("person")
class PersonEntity(
//    @Column("age", "number")
    var age: Int
)
