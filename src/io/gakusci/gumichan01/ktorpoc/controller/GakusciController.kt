package io.gakusci.gumichan01.ktorpoc.controller

import io.gakusci.gumichan01.ktorpoc.domain.model.DocumentEntry
import io.gakusci.gumichan01.ktorpoc.domain.service.SearchAggregator
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import kotlinx.coroutines.ExperimentalCoroutinesApi

class GakusciController {
    suspend fun home(call: ApplicationCall) {
        val staticPagesRelativesUrl = "/static"
        call.respondRedirect(staticPagesRelativesUrl)
    }

    @ExperimentalCoroutinesApi
    suspend fun search(call: ApplicationCall) {
        val queryKey = "q"
        val query: String? = call.request.queryParameters[queryKey]
        println("query value $query")

        if (query.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Bad request: no query text provided")
        } else {
            val entries: List<DocumentEntry> = SearchAggregator().search(query)
            call.respond(entries)
        }
    }
}