package com.hf.domain.model

data class Person(
    val name: String?,
    val height: String?,
    val birth_year: String?,
    val species: List<String>?,
    val homeworld: String?,
    val films: List<String>?,
    val url: String
)
