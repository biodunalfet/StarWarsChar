package com.hf.domain.model

data class SearchResult(
    val next: String?,
    val previous: String?,
    val count: Int?,
    val results: List<Person>,
    val query: String
)