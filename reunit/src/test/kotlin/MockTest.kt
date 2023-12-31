import org.example.mock.*

fun main() = runTests("Mocking") {
    test("mock") {
        val mock = mock<Simple>().stub {
            on { doIt() } doReturn 42
        }
        mock.doIt() shouldBe 42
        mock.doThat().isNull

        mock.stub {
            on { doThat() } doAnswer { "test" }
        }
        mock.doThat() shouldBe "test"

        failed {
            verify(mock).doNothing()
        }

        mock.doNothing()
        mock.doNothing()
        verify(mock, 2).doNothing()
    }
}

@ForTesting
class Simple {
    fun doIt() = 42
    fun doThat() = "hello"
    fun doNothing() = "nothing"
}
