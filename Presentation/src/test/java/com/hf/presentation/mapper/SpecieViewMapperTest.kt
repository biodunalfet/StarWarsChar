package com.hf.presentation.mapper

import com.hf.domain.model.Specie
import com.hf.presentation.model.SpecieView
import com.hf.presentation.test.factory.TestObjectFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SpecieViewMapperTest {

    lateinit var mapper: SpecieViewMapper

    @Before
    fun setUp() {
        mapper = SpecieViewMapper()
    }

    @Test
    fun mapsCorrectly() {

        val model = TestObjectFactory.makeSpecie()

        val mapped = mapper.mapToView(model)

        assertEqualData(mapped, model)
    }

    private fun assertEqualData(mapped: SpecieView, model: Specie) {

        assertEquals(mapped.homeworld, model.homeworld)
        assertEquals(mapped.language, model.language)
        assertEquals(mapped.homeworld, model.homeworld)
    }
}