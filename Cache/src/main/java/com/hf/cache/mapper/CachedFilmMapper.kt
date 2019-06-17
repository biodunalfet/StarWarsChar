package com.hf.cache.mapper

import com.hf.cache.model.CachedFilm
import com.hf.data.mapper.IUrlToIdMapper
import com.hf.data.model.FilmEntity
import javax.inject.Inject

class CachedFilmMapper @Inject constructor(
    private val urlToIdMapper: IUrlToIdMapper
) : CacheMapper<CachedFilm, FilmEntity> {

    override fun mapFromCached(type: CachedFilm): FilmEntity {
        return FilmEntity(type.title, type.release_date, type.opening_crawl, type.id)
    }

    override fun mapToCached(type: FilmEntity): CachedFilm? {
        return type.url?.let {
            val id = urlToIdMapper.apply(it)

            if (id == null) null
            else {
                CachedFilm(id, type.title, type.release_date, type.opening_crawl)
            }
        } ?: run {
            null
        }
    }


}