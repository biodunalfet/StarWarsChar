package com.hf.remote.model

import com.google.gson.annotations.SerializedName

data class SearchPersonResponseModel (@SerializedName("next") val next : String?,
                                      @SerializedName("results")val results : List<PersonModel>)
