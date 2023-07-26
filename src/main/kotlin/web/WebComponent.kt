package web

import org.reboot.app.annotation.Component
import org.reboot.app.annotation.EventListener

@Component
class WebComponent(
    private val config: WebConfig
) : Maker {

    @EventListener
    fun doIt() {
        println("do-it")
    }

    override fun make() {
        println("${config.fullName}:${config.message}")
    }
}
