package io.gakusci.gumichan01.ktorpoc

import io.gakusci.gumichan01.ktorpoc.controller.GakusciController
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.log
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
    routing {
        get("/") {
            GakusciController().home(call)
        }
        get("search") {
            log.info("search in ${Thread.currentThread().id} - ${Thread.currentThread().name}")
            GakusciController().search(call)
        }
    }
}