package com.hf.data.source

import com.hf.data.model.FilmEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import com.hf.data.repository.ICache
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import test.MapperFactory

class LocalDataSourceTest {

    lateinit var localDataSource: LocalDataSource
    @Mock lateinit var filmCacheMock: ICache<String, FilmEntity>
    @Mock lateinit var planetCacheMock: ICache<String, PlanetEntity>
    @Mock lateinit var specieCacheMock: ICache<String, SpecieEntity>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        localDataSource = LocalDataSource(filmCacheMock, planetCacheMock, specieCacheMock)
    }

    @Test
    fun getFilmWithIdCompletes() {
        stubGetFilmWithId(Single.just(MapperFactory.makeFilmEntity()))
        val testObserver = localDataSource.getFilmWithId(MapperFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getFilmByIdReturnsData() {
        val data = MapperFactory.makeFilmEntity()
        stubGetFilmWithId(Single.just(data))
        val testObserver = localDataSource.getFilmWithId(MapperFactory.randomString()).test()
        testObserver.assertValue(data)
    }

    @Test
    fun getFilmByIdCallsCacheSourceWithCorrectParams() {
        stubGetFilmWithId(Single.just(MapperFactory.makeFilmEntity()))
        val id = MapperFactory.randomString()
        localDataSource.getFilmWithId(id).test()
        verify(filmCacheMock).findItemById(id)
    }

    @Test
    fun getPlanetWithIdCompletes() {
        stubGetPlanetWithId(Single.just(MapperFactory.makePlanetEntity()))
        val testObserver = localDataSource.getPlanetWithId(MapperFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPlanetByIdReturnsData() {
        val data = MapperFactory.makePlanetEntity()
        stubGetPlanetWithId(Single.just(data))
        val testObserver = localDataSource.getPlanetWithId(MapperFactory.randomString()).test()
        testObserver.assertValue(data)
    }

    @Test
    fun getPlanetByIdCallsCacheSourceWithCorrectParams() {
        stubGetPlanetWithId(Single.just(MapperFactory.makePlanetEntity()))
        val id = MapperFactory.randomString()
        localDataSource.getPlanetWithId(id).test()
        verify(planetCacheMock).findItemById(id)
    }

    @Test
    fun getSpecieWithIdCompletes() {
        stubGetPlanetWithId(Single.just(MapperFactory.makePlanetEntity()))
        val testObserver = localDataSource.getPlanetWithId(MapperFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSpecieByIdReturnsData() {
        val data = MapperFactory.makeSpecieEntity()
        stubGetSpecieWithId(Single.just(data))
        val testObserver = localDataSource.getSpecieWithId(MapperFactory.randomString()).test()
        testObserver.assertValue(data)
    }

    @Test
    fun getSpecieByIdCallsCacheSourceWithCorrectParams() {
        stubGetSpecieWithId(Single.just(MapperFactory.makeSpecieEntity()))
        val id = MapperFactory.randomString()
        localDataSource.getSpecieWithId(id).test()
        verify(specieCacheMock).findItemById(id)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun searchPersonsCalled_errorThrown() {
        localDataSource.searchPersons(MapperFactory.randomString())
    }

    @Test
    fun saveSpecieCompletes() {
        stubsSaveSpecie(Completable.complete())
        val testObserver = localDataSource.saveSpecie(MapperFactory.randomString(), MapperFactory.makeSpecieEntity()).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveSpecieCallsCacheWithCorrectParams() {
        val specieCaptor = argumentCaptor<SpecieEntity>()
        val idCaptor = argumentCaptor<String>()

        val stubbedSpecie = MapperFactory.makeSpecieEntity()
        val stubbedId = MapperFactory.randomString()

        localDataSource.saveSpecie(stubbedId, stubbedSpecie)
        verify(specieCacheMock).saveItemWithId(idCaptor.capture(), specieCaptor.capture())
        assertEquals(idCaptor.firstValue, stubbedId)
        assertEquals(specieCaptor.firstValue, stubbedSpecie)
    }

    @Test
    fun savePlanetCompletes() {
        stubsSavePlanet(Completable.complete())
        val testObserver = localDataSource.savePlanet(MapperFactory.randomString(), MapperFactory.makePlanetEntity()).test()
        testObserver.assertComplete()
    }

    @Test
    fun savePlanetCallsCacheWithCorrectParams() {
        val planetCaptor = argumentCaptor<PlanetEntity>()
        val idCaptor = argumentCaptor<String>()

        val stubbedPlanet = MapperFactory.makePlanetEntity()
        val stubbedId = MapperFactory.randomString()

        localDataSource.savePlanet(stubbedId, stubbedPlanet)
        verify(planetCacheMock).saveItemWithId(idCaptor.capture(), planetCaptor.capture())
        assertEquals(idCaptor.firstValue, stubbedId)
        assertEquals(planetCaptor.firstValue, stubbedPlanet)
    }

    @Test
    fun saveFilmCompletes() {
        stubsSaveFilm(Completable.complete())
        val testObserver = localDataSource.saveFilm(MapperFactory.randomString(), MapperFactory.makeFilmEntity()).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveFilmCallsCacheWithCorrectParams() {
        val filmCaptor = argumentCaptor<FilmEntity>()
        val idCaptor = argumentCaptor<String>()

        val stubbedFilm = MapperFactory.makeFilmEntity()
        val stubbedId = MapperFactory.randomString()

        localDataSource.saveFilm(stubbedId, stubbedFilm)
        verify(filmCacheMock).saveItemWithId(idCaptor.capture(), filmCaptor.capture())
        assertEquals(idCaptor.firstValue, stubbedId)
        assertEquals(filmCaptor.firstValue, stubbedFilm)
    }

    private fun stubsSavePlanet(complete: Completable?) {
        whenever(planetCacheMock.saveItemWithId(any(), any())).thenReturn(complete)
    }

    private fun stubsSaveFilm(complete: Completable?) {
        whenever(filmCacheMock.saveItemWithId(any(), any())).thenReturn(complete)
    }

    private fun stubsSaveSpecie(complete: Completable?) {
        whenever(specieCacheMock.saveItemWithId(any(), any())).thenReturn(complete)
    }

    private fun stubGetFilmWithId(single: Single<FilmEntity>?) {
        whenever(filmCacheMock.findItemById(any())).thenReturn(single)
    }

    private fun stubGetPlanetWithId(single: Single<PlanetEntity>?) {
        whenever(planetCacheMock.findItemById(any())).thenReturn(single)
    }

    private fun stubGetSpecieWithId(single: Single<SpecieEntity>?) {
        whenever(specieCacheMock.findItemById(any())).thenReturn(single)
    }

}