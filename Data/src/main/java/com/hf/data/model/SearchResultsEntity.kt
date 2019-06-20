package com.hf.data.model

data class SearchResultsEntity(
    val next: String?,
    val previous: String?,
    val count: Int?,
    val results: List<PersonEntity>,
    var query: String = ""
)