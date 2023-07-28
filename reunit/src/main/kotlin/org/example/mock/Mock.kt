package org.example.mock

import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import java.lang.reflect.Method

val callStubs: MutableList<CallStub> = mutableListOf()
val realCalls: MutableList<RealCall> = mutableListOf()
val recording: MutableSet<Class<*>> = mutableSetOf()
var reading: Pair<Class<*>, Int>? = null

inline fun <reified T> mock() =
    Enhancer().run {
        setSuperclass(T::class.java)
        setCallback(createMethodInterceptor())
        create() as T
    }

inline fun <reified T: Any> verify(mock: T, count: Int = 1) =
    mock.apply {
        reading = mock.javaClass to count
    }

fun createMethodInterceptor() =
    MethodInterceptor { obj, method, args, proxy ->
        if (recording.contains(obj.javaClass)) {
            println("stubbing mode...")
            callStubs.last().method = method
            return@MethodInterceptor null
        }
        if (reading?.first == obj.javaClass) {
            val count = reading?.second ?: 1
            reading = null
            println("verifying mode...")
            if (realCalls.count { it.method == method && it.args.contentEquals(args) } == count) {
                return@MethodInterceptor null
            } else {
                throw Error("Call expected, but does not")
            }
        }
        realCalls += RealCall(method, args)
        callStubs.firstOrNull {
            it.method == method
        }?.result?.invoke()
    }

infix fun <T: Any, R> StubbedCall<T, R>.doReturn(result: R) =
    doAnswer { result }

infix fun <T: Any, R> StubbedCall<T, R>.doAnswer(result: () -> R) {
    callStubs.add(CallStub(null, result))
    function()
    println("do return")
}

inline fun <reified T: Any, R> T.on(noinline function: () -> R) =
    StubbedCall(this, function).also {
        println("on $function")
    }

data class StubbedCall<T: Any, R>(
    val instance: T,
    val function: () -> R
)

fun <T: Any> T.stub(function: T.() -> Unit): T {
    startRecord(this)
    function()
    endRecord(this)
    return this
}

fun <T: Any> startRecord(instance: T) = recording.add(instance.javaClass)
fun <T: Any> endRecord(instance: T) = recording.remove(instance.javaClass)

data class RealCall(
    val method: Method,
    val args: Array<out Any?>
)

data class CallStub(
    var method: Method?,
    var result: (() -> Any?)?
)