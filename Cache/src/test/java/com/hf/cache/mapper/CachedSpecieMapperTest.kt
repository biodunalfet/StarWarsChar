package com.hf.cache.mapper

import com.hf.cache.model.CachedSpecie
import com.hf.cache.test.factory.TestObjectsFactory
import com.hf.data.mapper.IUrlToIdMapper
import com.hf.data.model.SpecieEntity
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CachedSpecieMapperTest {

    private lateinit var cachedSpecieMapper: CachedSpecieMapper
    @Mock
    lateinit var urlToIdMapperMock : IUrlToIdMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cachedSpecieMapper = CachedSpecieMapper(urlToIdMapperMock)
    }

    @Test
    fun mapToCachedReturnCorrectData() {
        val stubbedId = TestObjectsFactory.randomInt()
        stubUrlToIdMapper(stubbedId)
        val entity = TestObjectsFactory.makeSpecieEntity()
        val result = cachedSpecieMapper.mapToCached(entity)

        assertEqualData(result, entity, stubbedId)
    }

    @Test
    fun mapFromCached() {

        val stubbedCachedSpecie = TestObjectsFactory.makeCachedSpecie()
        stubUrlToIdMapper(stubbedCachedSpecie.id.toInt())
        val entity = cachedSpecieMapper.mapFromCached(stubbedCachedSpecie)

        assertEqualData(stubbedCachedSpecie, entity, stubbedCachedSpecie.id.toInt())
    }

    private fun assertEqualData(result: CachedSpecie?, entity: SpecieEntity, stubbedId : Int) {

        assertEquals(result?.id, stubbedId.toString())
        assertEquals(result?.name, entity.name)
        assertEquals(result?.language, entity.language)
        assertEquals(result?.homeworld, entity.homeworld)

    }

    private fun stubUrlToIdMapper(id : Int) {
        whenever(urlToIdMapperMock.apply(any())).thenReturn(id.toString())
    }

}