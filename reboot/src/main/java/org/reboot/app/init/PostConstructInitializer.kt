package org.reboot.app.init

import org.reboot.app.ReBootContext
import org.reboot.app.annotation.PostConstruct
import org.reboot.app.utils.getAnnotatedMethods
import org.reboot.app.utils.hasAnnotatedMethod

object PostConstructInitializer : ClassInitializer {
    override fun init(classes: List<Class<*>>) {
        classes.filter { it.hasAnnotatedMethod<PostConstruct>() }
            .forEach { callPostConstructMethod(it) }
    }

    private fun callPostConstructMethod(it: Class<*>) {
        val bean = ReBootContext.getByClass(it)
        val postConstructMethod = it.getAnnotatedMethods<PostConstruct>()
            .first()
        postConstructMethod(bean)
    }
}


