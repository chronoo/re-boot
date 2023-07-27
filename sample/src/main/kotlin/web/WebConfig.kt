package web

import org.reboot.app.annotation.Config

@Config(prefix = "application")
class WebConfig {
    var message = "hello"
    var fullName = "app"
    var port = 123
}