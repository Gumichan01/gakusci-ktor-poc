package io.gakusci.gumichan01.ktorpoc.controller

import io.gakusci.gumichan01.ktorpoc.domain.model.DocumentEntry
import io.gakusci.gumichan01.ktorpoc.domain.service.SearchAggregator
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.thymeleaf.ThymeleafContent
import kotlinx.coroutines.ExperimentalCoroutinesApi

class GakusciController {

    @ExperimentalCoroutinesApi
    suspend fun search(call: ApplicationCall) {
        val queryKey = "q"
        val query: String? = call.request.queryParameters[queryKey]
        println("query value $query")

        if (query.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Bad request: no query text provided")
        } else {
            val entries: List<DocumentEntry> = SearchAggregator().search(query)
            call.respond(ThymeleafContent("search", mapOf("entries" to entries)))
        }
    }
}