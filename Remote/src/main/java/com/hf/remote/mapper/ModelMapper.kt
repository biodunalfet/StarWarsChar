package com.hf.remote.mapper

//model to entity
interface ModelMapper<in M, out E> {

    fun mapFromModel(model: M): E

}