package com.hf.data.mapper

import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import com.hf.domain.model.Planet
import com.hf.domain.model.Specie
import javax.inject.Inject

class SpecieMapper @Inject constructor()
    : EntityMapper<SpecieEntity, Specie> {

    override fun mapFromEntity(entity: SpecieEntity): Specie {
        return Specie(entity.name,
            entity.language, entity.homeworld, entity.url ?: "")
    }

    override fun mapToEntity(domain: Specie): SpecieEntity {
        return SpecieEntity(
            domain.name,
            domain.language, domain.homeworld,
            domain.url
        )
    }
}