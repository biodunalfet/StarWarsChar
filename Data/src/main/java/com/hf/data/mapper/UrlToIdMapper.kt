package com.hf.data.mapper

import javax.inject.Inject

class UrlToIdMapper @Inject constructor()
    : IUrlToIdMapper {

    override fun apply(url: String): String? {
        val paths = url.split("/")
        if (paths.isNotEmpty() && paths.size > 2) {
            return paths[paths.size - 2]
        }
        else {
            return ""
        }
    }

}