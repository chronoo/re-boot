import org.example.mock.*

fun main() {
    val mock = mock<Simple>().stub {
        on { doIt() } doReturn 42
    }
    println(mock.doIt())
    println(mock.doThat())

    mock.stub {
        on { doThat() } doReturn "test"
    }
    println(mock.doThat())
}


@ForTesting
class Simple {
    fun doIt() = 42
    fun doThat() = "hello"
}
