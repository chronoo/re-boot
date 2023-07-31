package org.redata

import org.redata.ConnectionManager.connection
import java.sql.Connection
import java.sql.DriverManager

fun main() {
    connection.use { connection ->
        println("valid ${connection.isValid(1)}")
        println("closed ${connection.isClosed}")
        connection.close()
        println("valid ${connection.isValid(1)}")
        println("closed ${connection.isClosed}")
    }
}

object ConnectionManager {
    private val url = "jdbc:h2:mem:test"
    private val user = "sa"
    private val password = "sa"

    val connection: Connection
        get() = DriverManager.getConnection(url, user, password)
}
