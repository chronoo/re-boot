package org.redata.tcl

import org.relog.info
import org.relog.warn
import org.relog.error
import java.sql.Connection

object TransactionManager {
    fun <T> runInTransaction(connection: Connection, action: () -> T): T =
        connection.run {
            try {
                info("start commit")
                autoCommit = false
                val result = action()
                commit()
                info("success commit")
                result
            } catch (e: Exception) {
                error("exception in transaction")
                warn("rollback transaction")
                rollback()
                throw TransactionException("Exception in transaction: ${e.message}")
            } finally {
                info("close connection")
                close()
            }
        }
}

class TransactionException(message: String) : RuntimeException(message)