package org.rebase

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.*
import kotlin.reflect.KClass

inline infix fun <reified T : Any> Any.instance(kClass: KClass<T>): T {
    return Proxy.newProxyInstance(
        Thread.currentThread().contextClassLoader,
        arrayOf(kClass.java),
        InvokeHandler(this)
    ) as T
}

inline fun <reified T> Any.dynamicCast(): T {
    return Proxy.newProxyInstance(
        Thread.currentThread().contextClassLoader,
        arrayOf(T::class.java),
        InvokeHandler(this)
    ) as T
}

class InvokeHandler(private val underlying: Any) : InvocationHandler {
    override fun invoke(proxy: Any, method: Method, args: Array<out Any?>?): Any? {
        for (fn in underlying.javaClass.methods) {
            if (fn.name == method.name && Arrays.equals(fn.parameterTypes, method.parameterTypes)) {
                return when (args) {
                    null -> fn.invoke(underlying)
                    else -> fn.invoke(underlying, *args)
                }
            }
        }
        throw UnsupportedOperationException("$method with $args")
    }
}
