package com.hf.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.hf.cache.db.StarWarsDatabase
import com.hf.cache.mapper.CachedSpecieMapper
import com.hf.cache.test.factory.TestObjectsFactory
import com.hf.data.mapper.UrlToIdMapper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class SpecieCacheImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        StarWarsDatabase::class.java)
        .allowMainThreadQueries().build()

    lateinit var cache : SpecieCacheImpl
    private val urlToIdMapper = UrlToIdMapper()
    private val mapper = CachedSpecieMapper(urlToIdMapper)

    @Before
    fun setUp() {
        cache = SpecieCacheImpl(mapper, database)
    }

    @Test
    fun saveSpecieWithIdCompletes() {

        val id = TestObjectsFactory.randomInt().toString()
        val cachedSpecie = TestObjectsFactory.makeSpecieEntity(TestObjectsFactory.makeUrl("species", true, id.toInt()))
        val testObserver = cache.saveItemWithId(id, cachedSpecie).test()
        testObserver.assertComplete()
    }

    @Test
    fun findItemByIdReturnsData() {

        val id = TestObjectsFactory.randomInt().toString()
        val specie = TestObjectsFactory.makeSpecieEntity(TestObjectsFactory.makeUrl("species", true, id.toInt()))
        cache.saveItemWithId(id, specie)

        val testObserver = cache.findItemById(id).test()
        testObserver.assertValue {
            it.homeworld == specie.homeworld
                    && it.language == specie.language
                    && it.name == specie.name
                    && it.url == urlToIdMapper.apply(specie.url!!)
        }

    }
}