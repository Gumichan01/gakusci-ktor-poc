package io.gakusci.gumichan01.ktorpoc.domain.service

import io.gakusci.gumichan01.springpoc.domain.model.DocumentEntry

interface IService {
    suspend fun searchForResourceName(query: String): List<DocumentEntry>
}