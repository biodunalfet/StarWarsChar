package com.hf.data.mapper

import com.hf.data.model.PlanetEntity
import com.hf.domain.model.Planet
import javax.inject.Inject

class PlanetMapper @Inject constructor()
    : EntityMapper<PlanetEntity, Planet> {

    override fun mapFromEntity(entity: PlanetEntity): Planet {
        return Planet(entity.name,
            entity.population, entity.url ?: "")
    }

    override fun mapToEntity(domain: Planet): PlanetEntity {
        return PlanetEntity(
            domain.name,
            domain.population,
            domain.url
        )
    }
}