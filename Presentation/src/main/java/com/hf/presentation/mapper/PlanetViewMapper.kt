package com.hf.presentation.mapper

import com.hf.domain.model.Planet
import com.hf.presentation.model.PlanetView
import javax.inject.Inject

class PlanetViewMapper @Inject constructor() : Mapper<PlanetView, Planet>{
    override fun mapToView(type: Planet): PlanetView {
        return PlanetView(type.name, type.population)
    }
}