package org.example.mock

val testResults = mutableListOf<TestResult>()

fun runTests(testActions: () -> Unit) {
    testActions()
    println("Test results: all: ${testResults.size}, success: ${testResults.count { it.success }}, failed: ${testResults.count { !it.success }}")
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
        print("$title - FAILED: ${e.stackTraceToString()}")
        TestResult(title, false, e.stackTraceToString())
    } finally {
        println()
    }
    testResults += result
}
