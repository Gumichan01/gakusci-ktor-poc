package io.gakusci.gumichan01.ktorpoc.restapi

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

// HAL Archives ouvertes response data
data class HalResponse(val response: HalResponseBody)

data class HalResponseBody(val numFound: Int, val start: Int, val docs: List<HalResultEntry>?)

data class HalResultEntry(
    val docid: Int, @JsonProperty("label_s") val label: String,
    @JsonProperty("uri_s") val uri: String
)

// Arxiv response data
data class Author(val name: String) {
    override fun toString() = name
}

// The display of the result should be normalized, no matter what the source is.
// But well, It's a POC, it is not designed to be in production
data class ArxivResultEntry(val authors: List<Author>, val title: String, val publishedDate: Date, val link: String)

fun ArxivResultEntry.label(): String = "$authors. $title. publishedDate"