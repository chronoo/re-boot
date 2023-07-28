package org.example.mock

val testResults = mutableListOf<TestResult>()

fun runTests(title: String, testActions: () -> Unit) {
    testActions()
    println("[$title] test results: all: ${testResults.size}, success: ${testResults.count { it.success }}, ${red { "failed: ${testResults.count { !it.success }}" }}")
    val failed = testResults.filter { !it.success }
        .map {
            "\t${it.title} - ${it.message.replace(Regex("([\t\n ]+)"), " ")}"
                .substring(0, 60) + "..."
        }
    println(red { failed.joinToString("\n") })
}

data class TestResult(
    val title: String,
    val success: Boolean,
    val message: String
)

fun test(title: String, action: () -> Unit) {
    println("$title - STARTED")
    val result = try {
        action()
        println("$title - SUCCESS")
        TestResult(title, true, "OK")
    } catch (e: Throwable) {
        print(red { "$title - FAILED: ${e.stackTraceToString()}" })
        TestResult(title, false, e.stackTraceToString())
    } finally {
        println()
    }
    testResults += result
}

fun failed(function: () -> Unit) {
    try {
        function()
    } catch (e: Throwable) {
        println(e.message + e.stackTraceToString())
        return
    }
    throw Error("Error expected, but not appeared")
}

fun red(message: () -> String) =
    "\u001b[31m${message()}\u001b[0m"
