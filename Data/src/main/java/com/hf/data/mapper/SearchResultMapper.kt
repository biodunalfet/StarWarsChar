package com.hf.data.mapper

import com.hf.data.model.SearchResultsEntity
import com.hf.domain.model.SearchResult
import javax.inject.Inject

class SearchResultMapper @Inject constructor(val mapper: PersonMapper) :
    EntityMapper<SearchResultsEntity, SearchResult> {

    override fun mapFromEntity(entity: SearchResultsEntity): SearchResult {
        return SearchResult(
            entity.next, entity.previous, entity.count, entity.results.map {
                mapper.mapFromEntity(it)
            }, entity.query
        )
    }

    override fun mapToEntity(domain: SearchResult): SearchResultsEntity {
        return SearchResultsEntity(domain.next, domain.previous, domain.count, domain.results.map {
            mapper.mapToEntity(it)
        }, domain.query)
    }
}