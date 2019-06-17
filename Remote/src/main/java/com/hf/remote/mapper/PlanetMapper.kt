package com.hf.remote.mapper

import com.hf.data.model.PlanetEntity
import com.hf.remote.model.PlanetModel
import javax.inject.Inject

class PlanetMapper @Inject constructor() : ModelMapper<PlanetModel, PlanetEntity> {

    override fun mapFromModel(model: PlanetModel): PlanetEntity {
        return PlanetEntity(model.name, model.population, model.url)
    }
}