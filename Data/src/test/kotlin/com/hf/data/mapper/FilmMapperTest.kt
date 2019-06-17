package com.hf.data.mapper

import com.hf.data.model.FilmEntity
import com.hf.domain.model.Film
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import test.MapperFactory

class FilmMapperTest {

    private lateinit var mapper : FilmMapper

    @Before
    fun setUp() {
        mapper = FilmMapper()
    }

    @Test
    fun mapFromEntityMapsCorrectly() {
        val entity = MapperFactory.makeFilmEntity()
        val model = mapper.mapFromEntity(entity)

        assertEqualData(entity, model)
    }

    @Test
    fun mapToEntityMapsCorrectly() {
        val model = MapperFactory.makeFilm()
        val entity = mapper.mapToEntity(model)

        assertEqualData(entity, model)
    }

    private fun assertEqualData(entity: FilmEntity, model: Film) {
        assertEquals(entity.opening_crawl, model.opening_crawl)
        assertEquals(entity.release_date, model.release_date)
        assertEquals(entity.title, model.title)
        assertEquals(entity.url, model.url)
    }

}