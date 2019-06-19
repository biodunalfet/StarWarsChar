package com.hf.cache.mapper

import com.hf.cache.model.CachedPlanet
import com.hf.cache.test.factory.TestObjectsFactory
import com.hf.data.mapper.IUrlToIdMapper
import com.hf.data.model.PlanetEntity
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CachedPlanetMapperTest {

    private lateinit var cachedPlanetMapper: CachedPlanetMapper
    @Mock
    lateinit var urlToIdMapperMock : IUrlToIdMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cachedPlanetMapper = CachedPlanetMapper(urlToIdMapperMock)
    }

    @Test
    fun mapToCachedReturnCorrectData() {
        val stubbedId = TestObjectsFactory.randomInt()
        stubUrlToIdMapper(stubbedId)
        val entity = TestObjectsFactory.makePlanetEntity()
        val result = cachedPlanetMapper.mapToCached(entity)

        assertEqualData(result, entity, stubbedId)
    }

    @Test
    fun mapFromCached() {

        val stubbedCachedPlanet = TestObjectsFactory.makeCachedPlanet()
        stubUrlToIdMapper(stubbedCachedPlanet.id.toInt())
        val entity = cachedPlanetMapper.mapFromCached(stubbedCachedPlanet)

        assertEqualData(stubbedCachedPlanet, entity, stubbedCachedPlanet.id.toInt())
    }

    private fun assertEqualData(result: CachedPlanet?, entity: PlanetEntity, stubbedId : Int) {

        assertEquals(result?.id, stubbedId.toString())
        assertEquals(result?.name, entity.name)
        assertEquals(result?.population, entity.population)

    }

    private fun stubUrlToIdMapper(id : Int) {
        whenever(urlToIdMapperMock.apply(any())).thenReturn(id.toString())
    }

}