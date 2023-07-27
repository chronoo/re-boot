import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.0"
    application
}

allOpen {
    annotation("org.reboot.app.annotation.Component")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":reboot")))
    implementation("cglib:cglib:3.3.0")
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
