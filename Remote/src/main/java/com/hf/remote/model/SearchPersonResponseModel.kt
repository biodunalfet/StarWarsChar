package com.hf.remote.model

import com.google.gson.annotations.SerializedName

data class SearchPersonResponseModel(
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("count") val count: Int?,
    @SerializedName("results") val results: List<PersonModel>
)
