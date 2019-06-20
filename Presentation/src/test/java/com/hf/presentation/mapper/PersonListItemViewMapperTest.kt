package com.hf.presentation.mapper

import com.hf.domain.model.Person
import com.hf.presentation.model.PersonListItemView
import com.hf.presentation.test.factory.TestObjectFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PersonListItemViewMapperTest {

    lateinit var mapper: PersonListItemViewMapper

    @Before
    fun setUp() {
        mapper = PersonListItemViewMapper()
    }

    @Test
    fun mapsCorrectly() {

        val model = TestObjectFactory.makePerson()

        val mapped = mapper.mapToView(model)

        assertEqualData(mapped, model)
    }

    private fun assertEqualData(mapped: PersonListItemView, model: Person) {
        assertEquals(mapped.name, model.name)
        assertEquals(mapped.height, model.height)
        assertEquals(mapped.birth_year, model.birth_year)
        assertEquals(mapped.homeworld, model.homeworld)
        assertEquals(mapped.url, model.url)
        assertEquals(mapped.films, model.films)
        assertEquals(mapped.species, model.species)
    }
}