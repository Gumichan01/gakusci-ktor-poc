package io.gakusci.gumichan01.ktorpoc

import com.fasterxml.jackson.databind.SerializationFeature
import io.gakusci.gumichan01.ktorpoc.controller.GakusciController
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.content.default
import io.ktor.http.content.static
import io.ktor.http.content.staticRootFolder
import io.ktor.jackson.jackson
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.File

fun main(args: Array<String>) {
    val watchPaths: List<String> = defineWatchPath(args)
    embeddedServer(
        Jetty,
        watchPaths = watchPaths,
        port = 8080,
        module = Application::gakusciModule
    ).apply { start(wait = true) }
}

private fun defineWatchPath(args: Array<String>): List<String> {
    return if (isDebugModeEnabled(args)) listOf("gakusci-ktor-poc") else emptyList()
}

private fun isDebugModeEnabled(args: Array<String>): Boolean {
    val debugArg = "-debug"
    return args.any { s -> s == debugArg }
}

@ExperimentalCoroutinesApi
fun Application.gakusciModule() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {

        static("static") {
            staticRootFolder = File("resources/static")
            default("index.html")
        }
        get("/") {
            GakusciController().home(call)
        }
        get("search") {
            GakusciController().search(call)
        }
    }
}