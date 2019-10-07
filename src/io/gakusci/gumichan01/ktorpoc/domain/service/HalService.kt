package io.gakusci.gumichan01.ktorpoc.domain.service

import io.gakusci.gumichan01.ktorpoc.restapi.HalClient
import io.gakusci.gumichan01.ktorpoc.domain.model.DocumentEntry

class HalService(private val halClient: HalClient) : IService {

    override suspend fun searchForResourceName(query: String): List<DocumentEntry> {
        return halClient.search(query).map { e -> DocumentEntry(e.label, e.uri) }
    }
}