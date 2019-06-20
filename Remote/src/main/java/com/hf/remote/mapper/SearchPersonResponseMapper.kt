package com.hf.remote.mapper

import com.hf.data.model.PersonEntity
import com.hf.data.model.SearchResultsEntity
import com.hf.remote.model.SearchPersonResponseModel
import javax.inject.Inject

class SearchPersonResponseMapper @Inject constructor() : ModelMapper<SearchPersonResponseModel, SearchResultsEntity> {

    override fun mapFromModel(model: SearchPersonResponseModel): SearchResultsEntity {
        return SearchResultsEntity(model.next, model.previous, model.count, model.results.map {
            PersonEntity(it.name, it.height, it.birth_year, it.species, it.homeworld, it.films, it.url)
        })
    }


}