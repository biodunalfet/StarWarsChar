package com.hf.cache

import android.util.Log
import com.hf.cache.db.StarWarsDatabase
import com.hf.cache.mapper.CachedPlanetMapper
import com.hf.data.model.PlanetEntity
import com.hf.data.repository.ICache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class PlanetCacheImpl @Inject constructor(
    private val mapper: CachedPlanetMapper,
    private val starWarsDatabase: StarWarsDatabase
) : ICache<String, PlanetEntity>
{

    override fun findItemById(id: String): Single<PlanetEntity> {
        return starWarsDatabase.planetDao().getPlanetById(id).map { mapper.mapFromCached(it) }
    }

    override fun saveItemWithId(id: String, item: PlanetEntity): Completable {

        mapper.mapToCached(item)?.let {
            starWarsDatabase.planetDao().insertPlanet(it)
        }

        return Completable.complete()

    }
}