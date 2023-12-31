import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.0"
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
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.guava:guava:32.1.1-jre")
    implementation(project(":reunit"))
    implementation(project(":relog"))
    implementation(project(":rebase"))
    implementation("cglib:cglib:3.3.0")
    implementation(kotlin("reflect"))
    implementation("com.h2database:h2:2.2.220")

}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}
