package com.hf.data.mapper

import com.hf.data.model.PersonEntity
import com.hf.domain.model.Person
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import test.MapperFactory

class PersonMapperTest {

    private lateinit var mapper: PersonMapper

    @Before
    fun setUp() {
        mapper = PersonMapper()
    }

    @Test
    fun mapFromEntityMapsCorrectly() {
        val entity = MapperFactory.makePersonEntity()
        val model = mapper.mapFromEntity(entity)

        assertEqualData(entity, model)
    }

    @Test
    fun mapToEntityMapsCorrectly() {
        val model = MapperFactory.makePerson()
        val entity = mapper.mapToEntity(model)

        assertEqualData(entity, model)
    }

    private fun assertEqualData(entity: PersonEntity, model: Person) {
        assertEquals(entity.birth_year, model.birth_year)
        assertEquals(entity.films, model.films)
        assertEquals(entity.height, model.height)
        assertEquals(entity.name, model.name)
        assertEquals(entity.species, model.species)
        assertEquals(entity.homeworld, model.homeworld)
        assertEquals(entity.url, model.url)
    }

}