package io.gakusci.gumichan01.ktorpoc.restapi

import io.ktor.client.HttpClient
import io.ktor.client.request.get

class HalClient {

    private val halUrl = "https://api.archives-ouvertes.fr/search/?q=%s&wt=json"
    private val client: HttpClient = HttpClient()

    suspend fun search(query: String): List<HalResultEntry> {
        val url = halUrl.format(query)
        val response: String = client.get(url)
        println("=====")
        println(response)
        println("===== END")
        return listOf()
    }
}