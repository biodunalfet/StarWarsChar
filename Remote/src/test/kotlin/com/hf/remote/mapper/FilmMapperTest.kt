package com.hf.remote.mapper

import com.hf.data.model.FilmEntity
import com.hf.remote.model.FilmModel
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import testFactory.TestObjectFactory

class FilmMapperTest {

    private val mapper = FilmMapper()

    @Test
    fun mapsFilmModelToEntityCorrectly() {

        val model = TestObjectFactory.makeFilmModel()
        val entity = mapper.mapFromModel(model)

        assertEqualData(model, entity)
    }

    private fun assertEqualData(model: FilmModel, entity: FilmEntity) {
        assertEquals(model.opening_crawl, entity.opening_crawl)
        assertEquals(model.url, entity.url)
        assertEquals(model.release_date, entity.release_date)
        assertEquals(model.title, entity.title)
    }
}