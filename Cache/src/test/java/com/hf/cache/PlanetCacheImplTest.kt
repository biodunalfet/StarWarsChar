package com.hf.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.hf.cache.db.StarWarsDatabase
import com.hf.cache.mapper.CachedPlanetMapper
import com.hf.cache.test.factory.TestObjectsFactory
import com.hf.data.mapper.UrlToIdMapper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class PlanetCacheImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        StarWarsDatabase::class.java)
        .allowMainThreadQueries().build()

    lateinit var cache : PlanetCacheImpl
    private val urlToIdMapper = UrlToIdMapper()
    private val mapper = CachedPlanetMapper(urlToIdMapper)

    @Before
    fun setUp() {
        cache = PlanetCacheImpl(mapper, database)
    }

    @Test
    fun savePlanetWithIdCompletes() {

        val id = TestObjectsFactory.randomInt().toString()
        val cachedPlanet = TestObjectsFactory.makePlanetEntity(TestObjectsFactory.makeUrl("planets", true, id.toInt()))
        val testObserver = cache.saveItemWithId(id, cachedPlanet).test()
        testObserver.assertComplete()
    }

    @Test
    fun findItemByIdReturnsData() {

        val id = TestObjectsFactory.randomInt().toString()
        val planet = TestObjectsFactory.makePlanetEntity(TestObjectsFactory.makeUrl("planets", true, id.toInt()))
        cache.saveItemWithId(id, planet)

        val testObserver = cache.findItemById(id).test()
        testObserver.assertValue {
            it.population == planet.population
                    && it.name == planet.name
                    && it.url == urlToIdMapper.apply(planet.url!!)
        }

    }
}