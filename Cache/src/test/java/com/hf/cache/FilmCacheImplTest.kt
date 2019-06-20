package com.hf.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.hf.cache.db.StarWarsDatabase
import com.hf.cache.mapper.CachedFilmMapper
import com.hf.cache.test.factory.TestObjectsFactory
import com.hf.data.mapper.UrlToIdMapper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class FilmCacheImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        StarWarsDatabase::class.java)
        .allowMainThreadQueries().build()

    lateinit var cache : FilmCacheImpl
    private val urlToIdMapper = UrlToIdMapper()
    private val mapper = CachedFilmMapper(urlToIdMapper)

    @Before
    fun setUp() {
        cache = FilmCacheImpl(mapper, database)
    }

    @Test
    fun saveFilmWithIdCompletes() {

        val id = TestObjectsFactory.randomInt().toString()
        val cachedFilm = TestObjectsFactory.makeFilmEntity(TestObjectsFactory.makeUrl("films", true, id.toInt()))
        val testObserver = cache.saveItemWithId(id, cachedFilm).test()
        testObserver.assertComplete()
    }

    @Test
    fun findItemByIdReturnsData() {

        val id = TestObjectsFactory.randomInt().toString()
        val film = TestObjectsFactory.makeFilmEntity(TestObjectsFactory.makeUrl("films", true, id.toInt()))
        cache.saveItemWithId(id, film)

        val testObserver = cache.findItemById(id).test()
        testObserver.assertValue {
            it.opening_crawl == film.opening_crawl && it.release_date == film.release_date && it.title == film.title && it.url == urlToIdMapper.apply(film.url!!)
        }

    }
}