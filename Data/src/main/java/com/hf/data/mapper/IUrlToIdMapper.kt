package com.hf.data.mapper

interface IUrlToIdMapper {
    fun apply(url: String): String?
}