package com.hf.remote.mapper

import com.hf.data.model.FilmEntity
import com.hf.remote.model.FilmModel
import javax.inject.Inject

class FilmMapper @Inject constructor() : ModelMapper<FilmModel, FilmEntity> {

    override fun mapFromModel(model: FilmModel): FilmEntity {
        return FilmEntity(model.title, model.release_date, model.opening_crawl, model.url)
    }
}