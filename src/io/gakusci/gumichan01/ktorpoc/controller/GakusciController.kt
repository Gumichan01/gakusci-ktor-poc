package io.gakusci.gumichan01.ktorpoc.controller

import io.gakusci.gumichan01.ktorpoc.domain.service.SearchAggregator
import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import kotlinx.coroutines.ExperimentalCoroutinesApi

class GakusciController {
    suspend fun home(call: ApplicationCall) {
        call.respondText("Hello World!", ContentType.Text.Plain, status = HttpStatusCode.OK)
    }

    @ExperimentalCoroutinesApi
    suspend fun search(call: ApplicationCall) {
        val queryKey = "q"
        val query: String? = call.request.queryParameters[queryKey]
        println("query value $query")

        if (query == null) {
            call.respond(HttpStatusCode.BadRequest)
        } else {
            query.run { SearchAggregator().search(this) }
                .also { call.respondText("search", ContentType.Text.Plain, status = HttpStatusCode.OK) }
        }
    }
}