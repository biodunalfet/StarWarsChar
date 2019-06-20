package com.hf.presentation.mapper

import com.hf.domain.model.Planet
import com.hf.presentation.model.PlanetView
import com.hf.presentation.test.factory.TestObjectFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PlanetViewMapperTest {

    lateinit var mapper: PlanetViewMapper

    @Before
    fun setUp() {
        mapper = PlanetViewMapper()
    }

    @Test
    fun mapsCorrectly() {

        val model = TestObjectFactory.makePlanet()

        val mapped = mapper.mapToView(model)

        assertEqualData(mapped, model)
    }

    private fun assertEqualData(mapped: PlanetView, model: Planet) {
        assertEquals(mapped.name, model.name)
        assertEquals(mapped.population, model.population)
    }

}