package org.example.mock

import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import java.lang.reflect.Method

val calls: MutableList<Call> = mutableListOf()

val recording: MutableSet<Class<*>> = mutableSetOf()

inline fun <reified T> mock() =
    Enhancer().run {
        setSuperclass(T::class.java)
        setCallback(createMethodInterceptor())
        create() as T
    }

fun createMethodInterceptor() =
    MethodInterceptor { obj, method, args, proxy ->
        if (recording.contains(obj.javaClass)) {
            println("stubbing mode...")
            calls.last().method = method
            return@MethodInterceptor null
        }
        calls.firstOrNull {
            it.method == method
        }?.result?.invoke()
    }

infix fun <T: Any, R> StubbedCall<T, R>.doReturn(result: R) =
    doAnswer { result }

infix fun <T: Any, R> StubbedCall<T, R>.doAnswer(result: () -> R) {
    calls.add(Call(null, result))
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

data class Call(
    var method: Method?,
    var result: (() -> Any?)?
)