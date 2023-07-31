package org.reboot.app.processor

import org.reboot.app.ReBootContext
import org.reboot.app.annotation.Component
import org.reboot.app.annotation.EventListener
import org.reboot.app.event.Event
import org.reboot.app.event.EventDispatcher
import org.reboot.app.utils.getAnnotatedMethods
import java.lang.reflect.Method

@Component
class EventListenerProcessor : Processor {
    override fun process() {
        val callbacks = ReBootContext.contextMap.map { bean ->
            bean.key.getAnnotatedMethods<EventListener>()
                .associateWith { bean.value.bean }
        }.flatMap { methodPair ->
            methodPair.entries.map { (method, instance) ->
                EventCallback(method, instance)
            }
        }

        for (callback in callbacks) {
            EventDispatcher.subscribe(callback.eventClass) { event ->
                callback.callback.invoke(callback.instance, event)
            }
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
