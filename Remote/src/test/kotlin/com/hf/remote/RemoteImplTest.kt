package com.hf.remote

import com.hf.data.model.FilmEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SearchResultsEntity
import com.hf.data.model.SpecieEntity
import com.hf.remote.mapper.FilmMapper
import com.hf.remote.mapper.PlanetMapper
import com.hf.remote.mapper.SearchPersonResponseMapper
import com.hf.remote.mapper.SpecieMapper
import com.hf.remote.model.FilmModel
import com.hf.remote.model.PlanetModel
import com.hf.remote.model.SearchPersonResponseModel
import com.hf.remote.model.SpecieModel
import com.hf.remote.service.StarWarsService
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
import testFactory.TestObjectFactory

class RemoteImplTest {

    @Mock lateinit var filmMapperMock : FilmMapper
    @Mock lateinit var planetMapperMock : PlanetMapper
    @Mock lateinit var specieMapperMock : SpecieMapper
    @Mock lateinit var responseMapperMock : SearchPersonResponseMapper
    @Mock lateinit var starWarsServiceMock : StarWarsService

    lateinit var remoteImpl: RemoteImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        remoteImpl = RemoteImpl(starWarsServiceMock, responseMapperMock, filmMapperMock, planetMapperMock, specieMapperMock)
    }

    @Test
    fun searchPersonCompletes() {
        stubServiceSearchPerson(TestObjectFactory.makeSearchPersonResponse())
        stubResponseMapper(TestObjectFactory.makeSearchResultsEntity())

        val testObserver = remoteImpl.searchPersons(TestObjectFactory.randomString(), TestObjectFactory.randomInt()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getFilmCompletes() {
        stubServiceGetFilm(TestObjectFactory.makeFilmModel())
        stubFilmMapper(TestObjectFactory.makeFilmEntity())

        val testObserver = remoteImpl.getFilm(TestObjectFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSpecieCompletes() {
        stubServiceGetSpecie(TestObjectFactory.makeSpecieModel())
        stubSpecieMapper(TestObjectFactory.makeSpecieEntity())

        val testObserver = remoteImpl.getSpecie(TestObjectFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPlanetCompletes() {
        stubServiceGetPlanet(TestObjectFactory.makePlanetModel())
        stubPlanetMapper(TestObjectFactory.makePlanetEntity())

        val testObserver = remoteImpl.getPlanet(TestObjectFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun searchPersonCallsService() {
        stubServiceSearchPerson(TestObjectFactory.makeSearchPersonResponse())
        stubResponseMapper(TestObjectFactory.makeSearchResultsEntity())

        remoteImpl.searchPersons(TestObjectFactory.randomString(), TestObjectFactory.randomInt())
        verify(starWarsServiceMock).searchPeople(any(), any())
    }

    @Test
    fun getFilmCallsService() {
        stubServiceGetFilm(TestObjectFactory.makeFilmModel())
        stubFilmMapper(TestObjectFactory.makeFilmEntity())

        remoteImpl.getFilm(TestObjectFactory.randomString()).test()
        verify(starWarsServiceMock).findFilmById(any())
    }

    @Test
    fun getPlanetCallsService() {
        stubServiceGetPlanet(TestObjectFactory.makePlanetModel())
        stubPlanetMapper(TestObjectFactory.makePlanetEntity())

        remoteImpl.getPlanet(TestObjectFactory.randomString())
        verify(starWarsServiceMock).findPlanetById(any())
    }

    @Test
    fun getSpecieCallsService() {
        stubServiceGetSpecie(TestObjectFactory.makeSpecieModel())
        stubSpecieMapper(TestObjectFactory.makeSpecieEntity())

        remoteImpl.getSpecie(TestObjectFactory.randomString())
        verify(starWarsServiceMock).findSpecieById(any())
    }

    @Test
    fun searchPerson_serviceIsCalledIWithCorrectParam() {
        val queryArgumentCaptor = argumentCaptor<String>()
        val pageArgumentCaptor = argumentCaptor<Int>()

        stubServiceSearchPerson(TestObjectFactory.makeSearchPersonResponse())
        stubResponseMapper(TestObjectFactory.makeSearchResultsEntity())

        val query = TestObjectFactory.randomString()
        val page = TestObjectFactory.randomInt()

        remoteImpl.searchPersons(query, page)
        verify(starWarsServiceMock).searchPeople(queryArgumentCaptor.capture(), pageArgumentCaptor.capture())
        assertEquals(query, queryArgumentCaptor.firstValue)
        assertEquals(page, pageArgumentCaptor.firstValue)
    }

    @Test
    fun getSpecie_serviceIsCalledIWithCorrectParam() {
        val argumentCaptor = argumentCaptor<String>()
        stubServiceGetSpecie(TestObjectFactory.makeSpecieModel())
        stubSpecieMapper(TestObjectFactory.makeSpecieEntity())

        val query = TestObjectFactory.randomString()

        remoteImpl.getSpecie(query)
        verify(starWarsServiceMock).findSpecieById(argumentCaptor.capture())
        assertEquals(query, argumentCaptor.firstValue)
    }

    @Test
    fun getPlanet_serviceIsCalledIWithCorrectParam() {
        val argumentCaptor = argumentCaptor<String>()
        stubServiceGetPlanet(TestObjectFactory.makePlanetModel())
        stubPlanetMapper(TestObjectFactory.makePlanetEntity())

        val query = TestObjectFactory.randomString()

        remoteImpl.getPlanet(query)
        verify(starWarsServiceMock).findPlanetById(argumentCaptor.capture())
        assertEquals(query, argumentCaptor.firstValue)
    }

    @Test
    fun getFilm_serviceIsCalledIWithCorrectParam() {
        val argumentCaptor = argumentCaptor<String>()
        stubServiceGetFilm(TestObjectFactory.makeFilmModel())
        stubFilmMapper(TestObjectFactory.makeFilmEntity())

        val query = TestObjectFactory.randomString()

        remoteImpl.getFilm(query)
        verify(starWarsServiceMock).findFilmById(argumentCaptor.capture())
        assertEquals(query, argumentCaptor.firstValue)
    }

    @Test
    fun searchPersonReturnsData() {

        stubServiceSearchPerson(TestObjectFactory.makeSearchPersonResponse())

        val data = TestObjectFactory.makeSearchResultsEntity()
        stubResponseMapper(data)

        val testObserver = remoteImpl.searchPersons(TestObjectFactory.randomString(), TestObjectFactory.randomInt()).test()
        testObserver.assertValue(data)
    }

    @Test
    fun getFilmReturnsData() {

        stubServiceGetFilm(TestObjectFactory.makeFilmModel())

        val data = TestObjectFactory.makeFilmEntity()
        stubFilmMapper(data)

        val testObserver = remoteImpl.getFilm(TestObjectFactory.randomString()).test()
        testObserver.assertValue(data)
    }

    @Test
    fun getPlanetReturnsData() {

        stubServiceGetPlanet(TestObjectFactory.makePlanetModel())

        val data = TestObjectFactory.makePlanetEntity()
        stubPlanetMapper(data)

        val testObserver = remoteImpl.getPlanet(TestObjectFactory.randomString()).test()
        testObserver.assertValue(data)
    }


    @Test
    fun getSpecieReturnsData() {

        stubServiceGetSpecie(TestObjectFactory.makeSpecieModel())

        val data = TestObjectFactory.makeSpecieEntity()
        stubSpecieMapper(data)

        val testObserver = remoteImpl.getSpecie(TestObjectFactory.randomString()).test()
        testObserver.assertValue(data)
    }

    private fun stubFilmMapper(data : FilmEntity) {
        whenever(filmMapperMock.mapFromModel(any())).thenReturn(data)
    }

    private fun stubSpecieMapper(data : SpecieEntity) {
        whenever(specieMapperMock.mapFromModel(any())).thenReturn(data)
    }

    private fun stubPlanetMapper(data : PlanetEntity) {
        whenever(planetMapperMock.mapFromModel(any())).thenReturn(data)
    }

    private fun stubServiceGetFilm(data : FilmModel) {
        whenever(starWarsServiceMock.findFilmById(any())).thenReturn(Single.just(data))
    }

    private fun stubServiceGetSpecie(data : SpecieModel) {
        whenever(starWarsServiceMock.findSpecieById(any())).thenReturn(Single.just(data))
    }

    private fun stubServiceGetPlanet(data : PlanetModel) {
        whenever(starWarsServiceMock.findPlanetById(any())).thenReturn(Single.just(data))
    }

    private fun stubResponseMapper(data: SearchResultsEntity) {
        whenever(responseMapperMock.mapFromModel(any())).thenReturn(data)
    }

    private fun stubServiceSearchPerson(data : SearchPersonResponseModel) {
        whenever(starWarsServiceMock.searchPeople(any(), any())).thenReturn(Observable.just(data))
    }
}