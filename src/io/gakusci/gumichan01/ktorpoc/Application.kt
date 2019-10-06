package io.gakusci.gumichan01.ktorpoc

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import io.ktor.server.jetty.JettyApplicationEngine

fun main() {
    val server: JettyApplicationEngine = embeddedServer(Jetty, port = 8082) {
        gakusciModule()
    }
    server.start(wait = true)
}

fun Application.gakusciModule() {
    routing {
        get("/") {
            call.respondText("Hello World!", ContentType.Text.Plain, status = HttpStatusCode.OK)
        }
    }
}