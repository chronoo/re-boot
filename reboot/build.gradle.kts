import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.0"
//    id("io.freefair.aspectj") version "5.1.1"
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
//    runtimeOnly("org.aspectj:aspectjrt:1.8.9")
//    runtimeOnly("org.aspectj:aspectjweaver:1.8.9")
//    implementation("org.aspectj:aspectjrt:1.9.6")
    implementation("cglib:cglib:3.3.0")
    implementation(kotlin("reflect"))
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}

//buildscript {
//    repositories {
//        maven {
//            url = uri("https://plugins.gradle.org/m2/")
//        }
//    }
//    dependencies {
//        classpath("io.freefair.gradle:aspectj-plugin:8.1.0")
//    }
//}
