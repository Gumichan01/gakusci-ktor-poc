package io.gakusci.gumichan01.ktorpoc.controller

import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText

class GakusciController {
    suspend fun home(call: ApplicationCall) {
        call.respondText("Hello World!", ContentType.Text.Plain, status = HttpStatusCode.OK)
    }

    suspend fun search(call: ApplicationCall) {
        val queryKey = "q"
        val queryValue: String? = call.request.queryParameters[queryKey]
        println("query value $queryValue")
        call.respondText("search", ContentType.Text.Plain, status = HttpStatusCode.OK)
    }
}