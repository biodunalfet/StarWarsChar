package com.hf.data.mapper

import com.hf.data.model.PlanetEntity
import com.hf.domain.model.Planet
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import test.MapperFactory

class PlanetMapperTest {

    private lateinit var mapper : PlanetMapper

    @Before
    fun setUp() {
        mapper = PlanetMapper()
    }

    @Test
    fun mapFromEntityMapsCorrectly() {
        val entity = MapperFactory.makePlanetEntity()
        val model = mapper.mapFromEntity(entity)

        assertEqualData(entity, model)
    }

    @Test
    fun mapToEntityMapsCorrectly() {
        val model = MapperFactory.makePlanet()
        val entity = mapper.mapToEntity(model)

        assertEqualData(entity, model)
    }

    private fun assertEqualData(entity: PlanetEntity, model: Planet) {
        assertEquals(entity.name, model.name)
        assertEquals(entity.population, model.population)
        assertEquals(entity.url, model.url)
    }

}