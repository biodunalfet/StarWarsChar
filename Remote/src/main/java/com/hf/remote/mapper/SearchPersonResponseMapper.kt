package com.hf.remote.mapper

import com.hf.data.model.PersonEntity
import com.hf.domain.model.Person
import com.hf.remote.model.SearchPersonResponseModel
import javax.inject.Inject

class SearchPersonResponseMapper @Inject constructor()
    : ModelMapper<SearchPersonResponseModel, List<PersonEntity>> {

    override fun mapFromModel(model: SearchPersonResponseModel): List<PersonEntity> {
        return model.results.map {
            PersonEntity(it.name, it.height, it.birth_year, it.species, it.homeworld,it.films, it.url)
        }
    }


}