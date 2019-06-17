package com.hf.presentation.mapper

import com.hf.domain.model.Specie
import com.hf.presentation.model.SpecieView
import javax.inject.Inject

class SpecieViewMapper @Inject constructor() : Mapper<SpecieView, Specie> {

    override fun mapToView(type: Specie): SpecieView {
        return SpecieView(type.name, type.language, type.homeworld)
    }
}