package com.hf.data

import com.hf.data.mapper.*
import com.hf.data.model.*
import com.hf.data.source.LocalDataSource
import com.hf.data.source.RemoteDataSource
import com.hf.domain.model.*
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import test.MapperFactory

class StarWarsRepositoryTest {

    lateinit var starWarsRepository: StarWarsRepository
    @Mock
    lateinit var remoteDataSourceMock: RemoteDataSource
    @Mock
    lateinit var localDataSourceMock: LocalDataSource
    @Mock
    lateinit var filmMapperMock: FilmMapper
    @Mock
    lateinit var planetMapperMock: PlanetMapper
    @Mock
    lateinit var searchResultMapper: SearchResultMapper
    @Mock
    lateinit var specieMapperMock: SpecieMapper
    @Mock
    lateinit var urlToIdMapperMock: UrlToIdMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        starWarsRepository = StarWarsRepository(
            filmMapperMock, planetMapperMock, specieMapperMock,
            searchResultMapper, urlToIdMapperMock, localDataSourceMock, remoteDataSourceMock
        )
    }

    @Test
    fun getPersonsCompletes() {
        stubSearchPerson(Observable.just(MapperFactory.makeSearchResultsEntity()))
        stubSearchResultMapper(MapperFactory.makeSearchResult())

        val testObserver = starWarsRepository.getPersons(MapperFactory.randomString(), MapperFactory.randomInt()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPersonReturnsData() {
        val data = MapperFactory.makeSearchResultsEntity()
        stubSearchPerson(Observable.just(data))
        val searchResultData = MapperFactory.makeSearchResult()
        stubSearchResultMapper(searchResultData)

        val testObserver = starWarsRepository.getPersons(MapperFactory.randomString(), MapperFactory.randomInt()).test()
        testObserver.assertValue(searchResultData)
    }

    @Test
    fun getPersonCallsRemoteDataSourceWithCorrectParams() {

        val queryCaptor = argumentCaptor<String>()
        val pageCaptor = argumentCaptor<Int>()
        val data = MapperFactory.makeSearchResultsEntity()

        stubSearchPerson(Observable.just(data))

        val searchResultData = MapperFactory.makeSearchResult()
        stubSearchResultMapper(searchResultData)

        val stubbedQuery = MapperFactory.randomString()
        val stubbedPage = MapperFactory.randomInt()

        starWarsRepository.getPersons(stubbedQuery, stubbedPage)

        verify(remoteDataSourceMock).searchPersons(queryCaptor.capture(), pageCaptor.capture())
        assertEquals(queryCaptor.firstValue, stubbedQuery)
        assertEquals(pageCaptor.firstValue, stubbedPage)
    }

    @Test
    fun getFilmByIdCompletes() {
        val stubbedId = MapperFactory.randomInt().toString()
        stubUrlMapper(stubbedId)

        stubGetFilmWithId(true, Single.just(MapperFactory.makeFilmEntity()))

        val filmData = MapperFactory.makeFilm()
        stubFilmMapper(filmData)

        val testObserver = starWarsRepository.getFilmById(MapperFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSpecieByIdCompletes() {
        val stubbedId = MapperFactory.randomInt().toString()
        stubUrlMapper(stubbedId)

        stubGetSpecieWithId(true, Single.just(MapperFactory.makeSpecieEntity()))

        val data = MapperFactory.makeSpecie()
        stubSpecieMapper(data)

        val testObserver = starWarsRepository.getSpecieById(MapperFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPlanetByIdCompletes() {
        val stubbedId = MapperFactory.randomInt().toString()
        stubUrlMapper(stubbedId)

        stubGetPlanetWithId(true, Single.just(MapperFactory.makePlanetEntity()))

        val data = MapperFactory.makePlanet()
        stubPlanetMapper(data)

        val testObserver = starWarsRepository.getPlanetById(MapperFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getFilmReturnsLocalDataWhenAvailable() {
        val stubbedId = MapperFactory.randomInt().toString()
        stubUrlMapper(stubbedId)
        stubGetFilmWithId(true, Single.just(MapperFactory.makeFilmEntity()))

        val filmData = MapperFactory.makeFilm()
        stubFilmMapper(filmData)

        val testObserver = starWarsRepository.getFilmById(MapperFactory.randomString()).test()
        testObserver.assertValue(filmData)
    }

    @Test
    fun getPlanetReturnsLocalDataWhenAvailable() {
        val stubbedId = MapperFactory.randomInt().toString()
        stubUrlMapper(stubbedId)
        stubGetPlanetWithId(true, Single.just(MapperFactory.makePlanetEntity()))

        val data = MapperFactory.makePlanet()
        stubPlanetMapper(data)

        val testObserver = starWarsRepository.getPlanetById(MapperFactory.randomString()).test()
        testObserver.assertValue(data)
    }

    @Test
    fun getSpecieReturnsLocalDataWhenAvailable() {
        val stubbedId = MapperFactory.randomInt().toString()
        stubUrlMapper(stubbedId)
        stubGetSpecieWithId(true, Single.just(MapperFactory.makeSpecieEntity()))

        val data = MapperFactory.makeSpecie()
        stubSpecieMapper(data)

        val testObserver = starWarsRepository.getSpecieById(MapperFactory.randomString()).test()
        testObserver.assertValue(data)
    }

    @Test
    fun getFilmDoesNotCallRemoteSourceWhenLocalDataIsAvailable() {
        val stubbedId = MapperFactory.randomInt().toString()
        stubUrlMapper(stubbedId)

        val stubbedFilmEntity = MapperFactory.makeFilmEntity()
        stubGetFilmWithId(true, Single.just(stubbedFilmEntity))

        val filmData = MapperFactory.makeFilm()
        stubFilmMapper(filmData)

        starWarsRepository.getFilmById(MapperFactory.randomString())
        verify(localDataSourceMock).getFilmWithId(stubbedId)
        verifyNoMoreInteractions(remoteDataSourceMock)
    }

    @Test
    fun getSpecieDoesNotCallRemoteSourceWhenLocalDataIsAvailable() {
        val stubbedId = MapperFactory.randomInt().toString()
        stubUrlMapper(stubbedId)

        val stubbedEntity = MapperFactory.makeSpecieEntity()
        stubGetSpecieWithId(true, Single.just(stubbedEntity))

        val data = MapperFactory.makeSpecie()
        stubSpecieMapper(data)

        starWarsRepository.getSpecieById(MapperFactory.randomString())
        verify(localDataSourceMock).getSpecieWithId(stubbedId)
        verifyNoMoreInteractions(remoteDataSourceMock)
    }

    @Test
    fun getPlanetDoesNotCallRemoteSourceWhenLocalDataIsAvailable() {
        val stubbedId = MapperFactory.randomInt().toString()
        stubUrlMapper(stubbedId)

        val stubbedEntity = MapperFactory.makePlanetEntity()
        stubGetPlanetWithId(true, Single.just(stubbedEntity))

        val data = MapperFactory.makePlanet()
        stubPlanetMapper(data)

        starWarsRepository.getPlanetById(MapperFactory.randomString())
        verify(localDataSourceMock).getPlanetWithId(stubbedId)
        verifyNoMoreInteractions(remoteDataSourceMock)
    }


    @Test
    fun getFilmReturnsRemoteDataWhenLocalIsNotAvailable() {
        val stubbedId = MapperFactory.randomInt().toString()
        stubUrlMapper(stubbedId)

        val stubbedFilmEntity = MapperFactory.makeFilmEntity()
        stubGetFilmWithId(false, Single.just(stubbedFilmEntity))

        val filmData = MapperFactory.makeFilm()
        stubFilmMapper(filmData)

        val testObserver = starWarsRepository.getFilmById(MapperFactory.randomString()).test()
        testObserver.assertValue(filmData)
    }


    private fun stubSearchResultMapper(domain: SearchResult) {
        whenever(searchResultMapper.mapFromEntity(any())).thenReturn(domain)
    }

    private fun stubPlanetMapper(domain: Planet) {
        whenever(planetMapperMock.mapFromEntity(any())).thenReturn(domain)
    }

    private fun stubSpecieMapper(domain: Specie) {
        whenever(specieMapperMock.mapFromEntity(any())).thenReturn(domain)
    }

    private fun stubFilmMapper(domain: Film) {
        whenever(filmMapperMock.mapFromEntity(any())).thenReturn(domain)
    }

    private fun stubUrlMapper(id: String) {
        whenever(urlToIdMapperMock.apply(any())).thenReturn(id)
    }

    private fun stubGetFilmWithId(local: Boolean, single: Single<FilmEntity>?) {

        if (local) {
            whenever(localDataSourceMock.getFilmWithId(any())).thenReturn(single)
        } else {
            whenever(localDataSourceMock.getFilmWithId(any())).thenReturn(Single.error(Exception()))
            whenever(remoteDataSourceMock.getFilmWithId(any())).thenReturn(single)
        }
    }

    private fun stubGetPlanetWithId(local: Boolean, single: Single<PlanetEntity>?) {

        if (local) {
            whenever(localDataSourceMock.getPlanetWithId(any())).thenReturn(single)
        } else {
            whenever(localDataSourceMock.getPlanetWithId(any())).thenReturn(Single.error(Exception()))
            whenever(remoteDataSourceMock.getPlanetWithId(any())).thenReturn(single)
        }
    }

    private fun stubGetSpecieWithId(local: Boolean, single: Single<SpecieEntity>?) {

        if (local) {
            whenever(localDataSourceMock.getSpecieWithId(any())).thenReturn(single)
        } else {
            whenever(localDataSourceMock.getSpecieWithId(any())).thenReturn(Single.error(Exception()))
            whenever(remoteDataSourceMock.getSpecieWithId(any())).thenReturn(single)
        }
    }

    private fun stubSearchPerson(observable: Observable<SearchResultsEntity>?) {
        whenever(remoteDataSourceMock.searchPersons(any(), any())).thenReturn(observable)
    }
}