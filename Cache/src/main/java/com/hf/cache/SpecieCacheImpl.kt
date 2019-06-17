package com.hf.cache

import com.hf.cache.db.StarWarsDatabase
import com.hf.cache.mapper.CachedSpecieMapper
import com.hf.data.model.SpecieEntity
import com.hf.data.repository.ICache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class SpecieCacheImpl @Inject constructor(
    private val mapper: CachedSpecieMapper,
    private val starWarsDatabase: StarWarsDatabase
) : ICache<String, SpecieEntity> {

    override fun findItemById(id: String): Single<SpecieEntity> {
        return starWarsDatabase.specieDao().getSpecieById(id).map { mapper.mapFromCached(it) }
    }

    override fun saveItemWithId(id: String, item: SpecieEntity): Completable {

        mapper.mapToCached(item)?.let {
            starWarsDatabase.specieDao().insertSpecie(it)
        }

        return Completable.complete()
    }
}