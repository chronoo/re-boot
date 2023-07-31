package org.reboot.app.event

import java.lang.reflect.Method

object EventDispatcher {
    private val listeners: MutableMap<Class<out Event>, MutableList<(event: Event) -> Unit>> = mutableMapOf()

    inline fun <reified T : Event> subscribe(noinline callback: (event: T) -> Unit) =
        subscribe(T::class.java, callback)

    fun <T: Event> subscribe(clazz: Class<T>, callback: (event: T) -> Unit) {
        val subscribers = listeners.computeIfAbsent(clazz) { mutableListOf() }
        subscribers += callback as (Event) -> Unit
        println("register listener to $clazz")
    }

    fun subscribe(callback: EventCallback) =
        subscribe(callback.eventClass) { event ->
            callback.callback.invoke(callback.instance, event)
        }

    fun emit(event: Event) {
        println("submit event $event")
        listeners.entries.filter { it.key.isInstance(event) }
            .flatMap { it.value }
            .forEach { listener ->
                listener(event)
            }
    }
}

data class EventCallback(
    val callback: Method,
    val instance: Any
) {
    val eventClass: Class<out Event> = callback.parameterTypes.first()
        .asSubclass(Event::class.java)
}
