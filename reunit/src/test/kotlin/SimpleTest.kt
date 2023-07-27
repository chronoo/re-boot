import org.example.mock.runTests
import org.example.mock.test

fun main() {
    runTests {
        test("some test") {
            println(42)
        }

        test("some test2") {
            throw IllegalAccessException()
        }
    }
}
