package org.reboot.app.processor

import org.reboot.app.Bean
import org.reboot.app.ReBootContext
import org.reboot.app.annotation.Component
import org.reboot.app.annotation.EventListener
import org.reboot.app.event.EventCallback
import org.reboot.app.event.EventDispatcher
import org.reboot.app.utils.getAnnotatedMethods

@Component
class EventListenerProcessor : Processor {
    override fun process() {
        ReBootContext.contextMap
            .flatMap { (clazz, bean) -> getEventCallbacks(clazz, bean) }
            .forEach(EventDispatcher::subscribe)
    }

    private fun getEventCallbacks(clazz: Class<*>, bean: Bean) =
        clazz.getAnnotatedMethods<EventListener>()
            .map { method -> EventCallback(method, bean.bean) }
}
