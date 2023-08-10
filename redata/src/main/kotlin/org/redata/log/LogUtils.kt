package org.redata.log

object ANSI {
    const val ANSI_GREEN = "\u001B[32m"
    const val ANSI_RESET = "\u001B[0m"
    const val ANSI_RED = "\u001B[31m"
    const val ANSI_YELLOW = "\u001B[33m"
}

fun info(message: String) {
    println("${ANSI.ANSI_GREEN}$message${ANSI.ANSI_RESET}")
}

fun error(message: String) {
    println("${ANSI.ANSI_RED}$message${ANSI.ANSI_RESET}")
}

fun warn(message: String) {
    println("${ANSI.ANSI_YELLOW}$message${ANSI.ANSI_RESET}")
}