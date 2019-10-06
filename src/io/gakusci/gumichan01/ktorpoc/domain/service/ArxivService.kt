package io.gakusci.gumichan01.ktorpoc.domain.service

import io.gakusci.gumichan01.springpoc.domain.model.DocumentEntry
import io.gakusci.gumichan01.ktorpoc.restapi.label

class ArxivService(private val arxivClient: ArxivClient) : IService {
    override suspend fun searchForResourceName(query: String): List<DocumentEntry> {
        return arxivClient.search(query).map { e -> DocumentEntry(e.label(), e.link) }
    }
}