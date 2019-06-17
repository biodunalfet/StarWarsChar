package com.hf.cache.mapper

import com.hf.cache.model.CachedSpecie
import com.hf.data.mapper.IUrlToIdMapper
import com.hf.data.model.SpecieEntity
import javax.inject.Inject

class CachedSpecieMapper @Inject constructor(
    private val urlToIdMapper: IUrlToIdMapper
) : CacheMapper<CachedSpecie, SpecieEntity> {

    override fun mapFromCached(type: CachedSpecie): SpecieEntity {
        return SpecieEntity(
            type.name,
            type.language, type.homeworld, type.id
        )
    }

    override fun mapToCached(type: SpecieEntity): CachedSpecie? {
        return type.url?.let {
            val id = urlToIdMapper.apply(it)

            if (id == null) null
            else {
                CachedSpecie(id, type.name, type.language, type.homeworld)
            }
        } ?: run {
            null
        }
    }


}