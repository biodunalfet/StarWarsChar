package com.hf.presentation.mapper

import com.hf.domain.model.Film
import com.hf.presentation.model.FilmView
import javax.inject.Inject

class FilmViewMapper @Inject constructor() : Mapper<FilmView, Film>{
    override fun mapToView(type: Film): FilmView {
        return FilmView(type.title, type.release_date, type.opening_crawl)
    }
}