package org.redata.connection

import java.sql.Connection
import java.sql.DriverManager

object ConnectionManager {
    private val url = "jdbc:h2:mem:test"
    private val user = "sa"
    private val password = "sa"

    val connection: Connection
        get() = DriverManager.getConnection(url, user, password)
}