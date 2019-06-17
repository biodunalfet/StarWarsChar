package com.hf.presentation.mapper

interface ViewMapper <in V1, out V2> {
    fun apply(v1 : V1) : V2
}