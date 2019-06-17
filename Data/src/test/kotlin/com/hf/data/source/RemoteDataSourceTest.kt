package com.hf.data.source

import com.hf.data.model.FilmEntity
import com.hf.data.model.PersonEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import com.hf.data.repository.IRemote
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import test.MapperFactory

class RemoteDataSourceTest {

    lateinit var remoteDataSource: RemoteDataSource
    @Mock
    lateinit var remoteMock: IRemote

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        remoteDataSource = RemoteDataSource(remoteMock)
    }

    @Test
    fun searchPersonsCompletes() {
        stubGetPerson(Observable.just(listOf(MapperFactory.makePersonEntity(), MapperFactory.makePersonEntity())))
        val testObserver = remoteDataSource.searchPersons(MapperFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun searchPersonReturnsData() {
        val data = listOf(
            MapperFactory.makePersonEntity(),
            MapperFactory.makePersonEntity(), MapperFactory.makePersonEntity()
        )
        stubGetPerson(Observable.just(data))
        val testObserver = remoteDataSource.searchPersons(MapperFactory.randomString()).test()
        testObserver.assertValue(data)
    }

    @Test
    fun getSearchPersonCallsCacheSourceWithCorrectParams() {

        val captor = argumentCaptor<String>()
        val data = listOf(
            MapperFactory.makePersonEntity(),
            MapperFactory.makePersonEntity(), MapperFactory.makePersonEntity()
        )
        stubGetPerson(Observable.just(data))
        val stubbedQuery = MapperFactory.randomString()

        remoteDataSource.searchPersons(stubbedQuery).test()

        verify(remoteMock).searchPersons(captor.capture())
        assertEquals(captor.firstValue, stubbedQuery)
    }

    @Test
    fun getFilmWithIdCompletes() {
        stubGetFilmWithId(Single.just(MapperFactory.makeFilmEntity()))
        val testObserver = remoteDataSource.getFilmWithId(MapperFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getFilmWithIdReturnsData() {
        val data = MapperFactory.makeFilmEntity()
        stubGetFilmWithId(Single.just(data))
        val testObserver = remoteDataSource.getFilmWithId(MapperFactory.randomString()).test()
        testObserver.assertValue(data)
    }

    @Test
    fun getFilmWithIdCallsRemoteWithCorrectParams() {

        val captor = argumentCaptor<String>()
        val data = MapperFactory.makeFilmEntity()
        stubGetFilmWithId(Single.just(data))
        val stubbedId = MapperFactory.randomString()

        remoteDataSource.getFilmWithId(stubbedId).test()

        verify(remoteMock).getFilm(captor.capture())
        assertEquals(captor.firstValue, stubbedId)
    }

    @Test
    fun getPlanetWithIdCompletes() {
        stubGetPlanetWithId(Single.just(MapperFactory.makePlanetEntity()))
        val testObserver = remoteDataSource.getPlanetWithId(MapperFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPlanetWithIdReturnsData() {
        val data = MapperFactory.makePlanetEntity()
        stubGetPlanetWithId(Single.just(data))
        val testObserver = remoteDataSource.getPlanetWithId(MapperFactory.randomString()).test()
        testObserver.assertValue(data)
    }

    @Test
    fun getPlanetWithIdCallsRemoteWithCorrectParams() {

        val captor = argumentCaptor<String>()
        val data = MapperFactory.makePlanetEntity()
        stubGetPlanetWithId(Single.just(data))
        val stubbedId = MapperFactory.randomString()

        remoteDataSource.getPlanetWithId(stubbedId).test()

        verify(remoteMock).getPlanet(captor.capture())
        assertEquals(captor.firstValue, stubbedId)
    }

    @Test
    fun getSpecieWithIdCompletes() {
        stubGetSpecieWithId(Single.just(MapperFactory.makeSpecieEntity()))
        val testObserver = remoteDataSource.getSpecieWithId(MapperFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSpecieWithIdReturnsData() {
        val data = MapperFactory.makeSpecieEntity()
        stubGetSpecieWithId(Single.just(data))
        val testObserver = remoteDataSource.getSpecieWithId(MapperFactory.randomString()).test()
        testObserver.assertValue(data)
    }

    @Test
    fun getSpecieWithIdCallsRemoteWithCorrectParams() {

        val captor = argumentCaptor<String>()
        val data = MapperFactory.makeSpecieEntity()
        stubGetSpecieWithId(Single.just(data))
        val stubbedId = MapperFactory.randomString()

        remoteDataSource.getSpecieWithId(stubbedId).test()

        verify(remoteMock).getSpecie(captor.capture())
        assertEquals(captor.firstValue, stubbedId)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveFilmCalled_errorThrown() {
        remoteDataSource.saveFilm(MapperFactory.randomString(), MapperFactory.makeFilmEntity())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun savePlanetCalled_errorThrown() {
        remoteDataSource.savePlanet(MapperFactory.randomString(), MapperFactory.makePlanetEntity())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveSpecieCalled_errorThrown() {
        remoteDataSource.saveSpecie(MapperFactory.randomString(), MapperFactory.makeSpecieEntity())
    }

    private fun stubGetPerson(observable: Observable<List<PersonEntity>>?) {
        whenever(remoteMock.searchPersons(any())).thenReturn(observable)
    }

    private fun stubGetFilmWithId(single: Single<FilmEntity>?) {
        whenever(remoteMock.getFilm(any())).thenReturn(single)
    }

    private fun stubGetPlanetWithId(single: Single<PlanetEntity>?) {
        whenever(remoteMock.getPlanet(any())).thenReturn(single)
    }

    private fun stubGetSpecieWithId(single: Single<SpecieEntity>?) {
        whenever(remoteMock.getSpecie(any())).thenReturn(single)
    }


}