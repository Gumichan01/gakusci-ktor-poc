package io.gakusci.gumichan01.ktorpoc

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
            call.respondText("Hello World!", ContentType.Text.Plain, status = HttpStatusCode.OK)
        }
        get("/search/") {
            val queryKey = "q"
            val queryValue: String? = call.request.queryParameters[queryKey]
            log.info("query value: $queryValue")
            call.respondText("search", ContentType.Text.Plain, status = HttpStatusCode.OK)
        }
    }
}