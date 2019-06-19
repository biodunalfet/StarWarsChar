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
class PlanetDaoTest {

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
    fun getPlanetByIdReturnsData() {
        val stubbedPlanet = TestObjectsFactory.makeCachedPlanet()
        database.planetDao().insertPlanet(stubbedPlanet)

        val testObserver = database.planetDao().getPlanetById(stubbedPlanet.id).test()
        testObserver.assertValue(stubbedPlanet)
    }


    @After
    fun tearDown() {
        database.close()
    }
}