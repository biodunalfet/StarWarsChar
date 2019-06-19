package com.hf.cache.mapper

import com.hf.cache.model.CachedFilm
import com.hf.cache.test.factory.TestObjectsFactory
import com.hf.data.mapper.IUrlToIdMapper
import com.hf.data.model.FilmEntity
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CachedFilmMapperTest {

    private lateinit var cachedFilmMapper: CachedFilmMapper
    @Mock lateinit var urlToIdMapperMock : IUrlToIdMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cachedFilmMapper = CachedFilmMapper(urlToIdMapperMock)
    }

    @Test
    fun mapToCachedReturnCorrectData() {
        val stubbedId = TestObjectsFactory.randomInt()
        stubUrlToIdMapper(stubbedId)
        val entity = TestObjectsFactory.makeFilmEntity()
        val result = cachedFilmMapper.mapToCached(entity)

        assertEqualData(result, entity, stubbedId)
    }

    @Test
    fun mapFromCached() {

        val stubbedCachedFilm = TestObjectsFactory.makeCachedFilm()
        stubUrlToIdMapper(stubbedCachedFilm.id.toInt())
        val entity = cachedFilmMapper.mapFromCached(stubbedCachedFilm)

        assertEqualData(stubbedCachedFilm, entity, stubbedCachedFilm.id.toInt())
    }

    private fun assertEqualData(result: CachedFilm?, entity: FilmEntity, stubbedId : Int) {

        assertEquals(result?.id, stubbedId.toString())
        assertEquals(result?.opening_crawl, entity.opening_crawl)
        assertEquals(result?.release_date, entity.release_date)
        assertEquals(result?.title, entity.title)

    }

    private fun stubUrlToIdMapper(id : Int) {
        whenever(urlToIdMapperMock.apply(any())).thenReturn(id.toString())
    }



}