package com.hf.data.mapper

import com.hf.data.model.FilmEntity
import com.hf.data.model.SpecieEntity
import com.hf.domain.model.Film
import com.hf.domain.model.Specie
import javax.inject.Inject

class FilmMapper @Inject constructor()
    : EntityMapper<FilmEntity, Film> {

    override fun mapFromEntity(entity: FilmEntity): Film {
        return Film(entity.title,
            entity.release_date, entity.opening_crawl, entity.url ?: "")
    }

    override fun mapToEntity(domain: Film): FilmEntity {
        return FilmEntity(
            domain.title,
            domain.release_date, domain.opening_crawl,
            domain.url
        )
    }
}