package com.hf.remote.mapper

import com.hf.data.model.PlanetEntity
import com.hf.remote.model.PlanetModel
import org.junit.Assert.assertEquals
import org.junit.Test
import testFactory.TestObjectFactory

class PlanetMapperTest {

    private val mapper = PlanetMapper()

    @Test
    fun mapsPlanetModelToEntityCorrectly() {

        val model = TestObjectFactory.makePlanetModel()
        val entity = mapper.mapFromModel(model)

        assertEqualData(model, entity)
    }

    private fun assertEqualData(model: PlanetModel, entity: PlanetEntity) {
        assertEquals(model.url, entity.url)
        assertEquals(model.population, entity.population)
        assertEquals(model.name, entity.name)
    }
}