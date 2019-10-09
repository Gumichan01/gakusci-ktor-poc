package io.gakusci.gumichan01.ktorpoc.restapi

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get

class HalClient {

    private val halUrl = "https://api.archives-ouvertes.fr/search/?q=%s&wt=json"
    private val client: HttpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    suspend fun search(query: String): List<HalResultEntry> {
        val url = halUrl.format(query)
        val entries: List<HalResultEntry> = client.get<HalResponse>(url).response.docs ?: listOf()
        client.close()
        return entries
    }
}