package com.hf.data.mapper

import com.hf.data.model.SpecieEntity
import com.hf.domain.model.Specie
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import test.MapperFactory

class SpecieMapperTest {

    private lateinit var mapper : SpecieMapper

    @Before
    fun setUp() {
        mapper = SpecieMapper()
    }

    @Test
    fun mapFromEntityMapsCorrectly() {
        val entity = MapperFactory.makeSpecieEntity()
        val model = mapper.mapFromEntity(entity)

        assertEqualData(entity, model)
    }

    @Test
    fun mapToEntityMapsCorrectly() {
        val model = MapperFactory.makeSpecie()
        val entity = mapper.mapToEntity(model)

        assertEqualData(entity, model)
    }

    private fun assertEqualData(entity: SpecieEntity, model: Specie) {
        assertEquals(entity.homeworld, model.homeworld)
        assertEquals(entity.language, model.language)
        assertEquals(entity.name, model.name)
        assertEquals(entity.url, model.url)
    }
}