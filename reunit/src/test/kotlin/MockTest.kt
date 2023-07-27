import org.example.mock.*

fun main() = runTests("Mocking") {
    test("mock") {
        val mock = mock<Simple>().stub {
            on { doIt() } doReturn 42
        }
        assertEquals(42, mock.doIt())
        assertEquals(null, mock.doThat())

        mock.stub {
            on { doThat() } doReturn "test"
        }
        assertEquals("test", mock.doThat())
    }
}


@ForTesting
class Simple {
    fun doIt() = 42
    fun doThat() = "hello"
}
