package web

import org.reboot.app.annotation.Component

@Component
class WebComponent(
    private val config: WebConfig
) {
    fun make() {
        println(config.message)
    }
}