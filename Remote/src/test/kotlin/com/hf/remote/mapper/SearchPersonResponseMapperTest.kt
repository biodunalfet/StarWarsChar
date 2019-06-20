package com.hf.remote.mapper

import com.hf.data.model.PersonEntity
import com.hf.remote.model.PersonModel
import org.junit.Assert.assertEquals
import org.junit.Test
import testFactory.TestObjectFactory

class SearchPersonResponseMapperTest {

    private val mapper = SearchPersonResponseMapper()

    @Test
    fun mapsFilmModelToEntityCorrectly() {

        val model = TestObjectFactory.makeSearchPersonResponse()
        val entity = mapper.mapFromModel(model)

        assertEquals(model.count, entity.count)
        assertEquals(model.next, entity.next)
        assertEquals(model.previous, entity.previous)

        model.results.forEachIndexed { index, personModel ->
            assertEqualData(personModel, entity.results[index])
        }
    }

    private fun assertEqualData(model: PersonModel, entity: PersonEntity) {
        assertEquals(model.birth_year, entity.birth_year)
        assertEquals(model.films, entity.films)
        assertEquals(model.height, entity.height)
        assertEquals(model.homeworld, entity.homeworld)
        assertEquals(model.name, entity.name)
        assertEquals(model.species, entity.species)
        assertEquals(model.url, entity.url)
    }
}