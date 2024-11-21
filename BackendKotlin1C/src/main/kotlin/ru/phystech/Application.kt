package ru.phystech

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import ru.phystech.routing.configureRouting

fun main(args: Array<String>) {
    io.ktor.server.jetty.jakarta.EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    configureRouting()
}
