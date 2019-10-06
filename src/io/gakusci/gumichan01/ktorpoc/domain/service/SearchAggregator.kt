package io.gakusci.gumichan01.ktorpoc.domain.service

import io.gakusci.gumichan01.ktorpoc.restapi.HalClient
import io.gakusci.gumichan01.springpoc.domain.model.DocumentEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.receiveOrNull

class SearchAggregator {

    private val services: Set<IService> = setOf(HalService(HalClient()), ArxivService(ArxivClient()))

    // Now you understand why this POC must not be in production
    @ExperimentalCoroutinesApi
    suspend fun search(query: String): List<DocumentEntry> {
        val channels: List<ReceiveChannel<DocumentEntry>> = processQueryToServices(query)
        val documentEntries: List<DocumentEntry> = consumeResults(channels)
        println("Got ${documentEntries.size} entries")
        return documentEntries
    }

    // Producers
    @ExperimentalCoroutinesApi
    private fun processQueryToServices(query: String): List<ReceiveChannel<DocumentEntry>> {
        return services.map { s ->
            CoroutineScope(Dispatchers.Default).produce<DocumentEntry>(capacity = 64) {
                suspendingSearch(s, query)
            }
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun ProducerScope<DocumentEntry>.suspendingSearch(service: IService, query: String) {
        service.searchForResourceName(query).forEach<DocumentEntry> { doc -> send(doc) }
        close()
    }

    // Consumer
    @ExperimentalCoroutinesApi
    private suspend fun consumeResults(channels: List<ReceiveChannel<DocumentEntry>>): MutableList<DocumentEntry> {
        val documentEntries: MutableList<DocumentEntry> = mutableListOf()
        //runBlocking {
        while (channels.any { channel -> !channel.isClosedForReceive }) {
            channels.forEach { channel ->
                val element: DocumentEntry? = channel.receiveOrNull()
                element?.let { documentEntries.add(it) }
            }
        }
        //}
        return documentEntries
    }
}