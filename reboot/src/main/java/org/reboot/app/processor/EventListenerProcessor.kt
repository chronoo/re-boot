package org.reboot.app.processor

import org.reboot.app.ReBootContext
import org.reboot.app.annotation.Component
import org.reboot.app.annotation.EventListener
import org.reboot.app.event.Event
import org.reboot.app.event.EventDispatcher
import org.reboot.app.utils.getAnnotatedMethods

@Component
class EventListenerProcessor : Processor {
    override fun process() {
        val methodPairs = ReBootContext.contextMap.map { bean ->
            bean.key.getAnnotatedMethods<EventListener>()
                .associateWith { bean.value.bean }
        }

        methodPairs.forEach { methodPair ->
            methodPair.entries.forEach { pair ->
                val callback = pair.key
                val eventClass = callback.parameterTypes.first()
                    .asSubclass(Event::class.java)
                EventDispatcher.subscribe(eventClass) { event ->
                    callback.invoke(pair.value, event)
                }
            }
        }
    }
}
