package com.hf.remote.model

import com.google.gson.annotations.SerializedName

data class PersonModel(
    @SerializedName("name") val name: String?,
    @SerializedName("height") val height: String?,
    @SerializedName("birth_year") val birth_year: String?,
    @SerializedName("species") val species: List<String>?,
    @SerializedName("homeworld") val homeworld: String?,
    @SerializedName("films") val films: List<String>?,
    @SerializedName("url") val url: String
)