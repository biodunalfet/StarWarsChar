package com.hf.cache.mapper

import com.hf.cache.model.CachedPlanet
import com.hf.data.mapper.IUrlToIdMapper
import com.hf.data.model.PlanetEntity
import javax.inject.Inject

class CachedPlanetMapper @Inject constructor(
    private val urlToIdMapper: IUrlToIdMapper
) : CacheMapper<CachedPlanet, PlanetEntity> {

    override fun mapFromCached(type: CachedPlanet): PlanetEntity {
        return PlanetEntity(
            type.name,
            type.population, type.id
        )
    }

    override fun mapToCached(type: PlanetEntity): CachedPlanet? {
        return type.url?.let {
            val id = urlToIdMapper.apply(it)

            if (id == null) null
            else {
                CachedPlanet(id, type.name, type.population)
            }
        } ?: run {
            null
        }
    }


}