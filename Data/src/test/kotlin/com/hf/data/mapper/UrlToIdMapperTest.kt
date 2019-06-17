package com.hf.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import test.MapperFactory

class UrlToIdMapperTest {

    private lateinit var mapper: UrlToIdMapper

    @Before
    fun setUp() {
        mapper = UrlToIdMapper()
    }

    @Test
    fun convertsUrlToIdCorrectly() {

        val urls = mutableListOf<String>()
        val ids = listOf(34, 563, 7, 2)

        urls.add(MapperFactory.randomUrl("species", true, ids[0]))
        urls.add(MapperFactory.randomUrl("planets", true, ids[1]))
        urls.add(MapperFactory.randomUrl("films", true, ids[2]))
        urls.add(MapperFactory.randomUrl("persons", true, ids[3]))

        urls.forEachIndexed { index, url ->
            val id = mapper.apply(url)
            assertEquals(id, ids[index].toString())
        }

    }

    @Test
    fun invalidUrlPassed_returnsEmptyString() {
        val url = MapperFactory.randomString()
        val id = mapper.apply(url)

        assertEquals(id, "")
    }


}