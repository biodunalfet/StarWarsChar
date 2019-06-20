package com.hf.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.hf.cache.db.StarWarsDatabase
import com.hf.cache.test.factory.TestObjectsFactory
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class FilmDaoTest {

    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: StarWarsDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            StarWarsDatabase::class.java)
            .allowMainThreadQueries().build()
    }

    @Test
    fun getFilmByIdReturnsData() {
        val stubbedFilm = TestObjectsFactory.makeCachedFilm()
        database.filmDao().insertFilm(stubbedFilm)

        val testObserver = database.filmDao().getFilmById(stubbedFilm.id).test()
        testObserver.assertValue(stubbedFilm)
    }


    @After
    fun tearDown() {
        database.close()
    }
}