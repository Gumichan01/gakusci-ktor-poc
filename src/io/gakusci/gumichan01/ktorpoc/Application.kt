package io.gakusci.gumichan01.ktorpoc

import io.gakusci.gumichan01.ktorpoc.controller.GakusciController
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty

fun main() {
    embeddedServer(
        Jetty,
        watchPaths = listOf("gakusci-ktor-poc"),
        port = 8080,
        module = Application::gakusciModule
    ).apply { start(wait = true) }
}

fun Application.gakusciModule() {
    routing {
        get("/") {
            GakusciController().home(call)
        }
        get("search") {
            GakusciController().search(call)
        }
    }
}