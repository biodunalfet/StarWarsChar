package com.hf.presentation.mapper

import com.hf.domain.model.Film
import com.hf.presentation.model.FilmView
import com.hf.presentation.test.factory.TestObjectFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilmViewMapperTest {

    lateinit var mapper: FilmViewMapper

    @Before
    fun setUp() {
        mapper = FilmViewMapper()
    }

    @Test
    fun mapsCorrectly() {

        val model = TestObjectFactory.makeFilm()

        val mapped = mapper.mapToView(model)

        assertEqualData(mapped, model)
    }

    private fun assertEqualData(mapped: FilmView, model: Film) {

        assertEquals(mapped.release_date, model.release_date)
        assertEquals(mapped.opening_crawl, model.opening_crawl)
        assertEquals(mapped.title, model.title)
    }
}