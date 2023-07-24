package web

import org.reboot.app.annotation.Component

@Component
class WebComponent(
    private val config: WebConfig
) : Maker {
    override fun make() {
        println("${config.fullName}:${config.message}")
    }
}
