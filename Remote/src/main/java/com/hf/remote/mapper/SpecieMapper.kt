package com.hf.remote.mapper

import com.hf.data.model.SpecieEntity
import com.hf.remote.model.SpecieModel
import javax.inject.Inject

class SpecieMapper @Inject constructor() : ModelMapper<SpecieModel, SpecieEntity> {

    override fun mapFromModel(model: SpecieModel): SpecieEntity {
        return SpecieEntity(model.name, model.language, model.homeworld, model.url)
    }
}