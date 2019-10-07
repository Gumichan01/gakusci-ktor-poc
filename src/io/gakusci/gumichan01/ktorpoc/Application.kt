package io.gakusci.gumichan01.ktorpoc

import com.fasterxml.jackson.databind.SerializationFeature
import io.gakusci.gumichan01.ktorpoc.controller.GakusciController
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import kotlinx.coroutines.ExperimentalCoroutinesApi

fun main() {
    embeddedServer(
        Jetty,
        watchPaths = listOf("gakusci-ktor-poc"),
        port = 8080,
        module = Application::gakusciModule
    ).apply { start(wait = true) }
}

@ExperimentalCoroutinesApi
fun Application.gakusciModule() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        get("/") {
            GakusciController().home(call)
        }
        get("search") {
            GakusciController().search(call)
        }
    }
}