package com.hf.remote.mapper

import com.hf.data.model.SpecieEntity
import com.hf.remote.model.SpecieModel
import org.junit.Assert.assertEquals
import org.junit.Test
import testFactory.TestObjectFactory

class SpecieMapperTest {

    private val mapper = SpecieMapper()

    @Test
    fun mapsSpecieModelToEntityCorrectly() {

        val model = TestObjectFactory.makeSpecieModel()
        val entity = mapper.mapFromModel(model)

        assertEqualData(model, entity)
    }

    private fun assertEqualData(model: SpecieModel, entity: SpecieEntity) {
        assertEquals(model.homeworld, entity.homeworld)
        assertEquals(model.url, entity.url)
        assertEquals(model.name, entity.name)
        assertEquals(model.language, entity.language)
    }
}