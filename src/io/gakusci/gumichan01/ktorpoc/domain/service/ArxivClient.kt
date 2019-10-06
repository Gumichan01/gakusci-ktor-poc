package io.gakusci.gumichan01.ktorpoc.domain.service

import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import io.gakusci.gumichan01.ktorpoc.restapi.ArxivResultEntry
import io.gakusci.gumichan01.ktorpoc.restapi.Author
import java.net.URL

class ArxivClient {

    private val arxivUrl = "https://export.arxiv.org/api/query?search_query=%s"

    fun search(query: String): List<ArxivResultEntry> {
        val url = arxivUrl.format(query)
        return SyndFeedInput().build(XmlReader(URL(url))).entries.map { e ->
            ArxivResultEntry(e.authors.map { a -> Author(a.name) }, e.title, e.publishedDate, e.link)
        }
    }
}