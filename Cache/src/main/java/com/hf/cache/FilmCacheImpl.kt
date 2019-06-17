package com.hf.cache

import com.hf.cache.db.StarWarsDatabase
import com.hf.cache.mapper.CachedFilmMapper
import com.hf.data.model.FilmEntity
import com.hf.data.repository.ICache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class FilmCacheImpl @Inject constructor(
    private val mapper: CachedFilmMapper,
    private val starWarsDatabase: StarWarsDatabase
) : ICache<String, FilmEntity>
    {

    override fun findItemById(id: String): Single<FilmEntity> {
        return starWarsDatabase.filmDao().getFilmById(id).map { mapper.mapFromCached(it) }
    }

    override fun saveItemWithId(id: String, item: FilmEntity): Completable {

        mapper.mapToCached(item)?.let {
            starWarsDatabase.filmDao().insertFilm(it)
        }

        return Completable.complete()

    }
}