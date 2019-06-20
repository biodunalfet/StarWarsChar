package com.hf.presentation.mapper

import com.hf.presentation.model.PersonListItemView
import com.hf.presentation.model.PersonView
import com.hf.presentation.test.factory.TestObjectFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PersonViewMapperTest {

    lateinit var mapper: PersonViewMapper

    @Before
    fun setUp() {
        mapper = PersonViewMapper()
    }

    @Test
    fun mapsCorrectly() {

        val data = TestObjectFactory.makePersonListItemView()

        val mapped = mapper.apply(data)

        assertEqualData(data, mapped)
    }

    private fun assertEqualData(mapped: PersonListItemView, model: PersonView) {
        assertEquals(mapped.name, model.name)
        assertEquals(mapped.height, model.height)
        assertEquals(mapped.birth_year, model.birth_year)
    }
}