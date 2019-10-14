package io.gakusci.gumichan01.ktorpoc.domain.service

import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import io.gakusci.gumichan01.ktorpoc.restapi.ArxivResultEntry
import io.gakusci.gumichan01.ktorpoc.restapi.Author
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class ArxivClient {

    private val arxivUrl = "https://export.arxiv.org/api/query?search_query=%s"

    suspend fun search(query: String): List<ArxivResultEntry> {
        val url = arxivUrl.format(query)
        val xmlReader: XmlReader = withContext(Dispatchers.IO) {
            XmlReader(URL(url))
        }
        return SyndFeedInput().build(xmlReader).entries.map { e ->
            ArxivResultEntry(e.authors.map { a -> Author(a.name) }, e.title, e.publishedDate, e.link)
        }
    }
}