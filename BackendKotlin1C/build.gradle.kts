
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    kotlin("plugin.serialization") version "2.0.20"
}

group = "ru.phystech"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.jetty.jakarta.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.jetty.jakarta)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    implementation("io.ktor:ktor-server-content-negotiation:3.0.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.1")
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
