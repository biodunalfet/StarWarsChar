package com.hf.data

import com.hf.data.mapper.*
import com.hf.data.model.FilmEntity
import com.hf.data.model.PersonEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import com.hf.data.source.LocalDataSource
import com.hf.data.source.RemoteDataSource
import com.hf.domain.model.Film
import com.hf.domain.model.Person
import com.hf.domain.model.Planet
import com.hf.domain.model.Specie
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
    @Mock lateinit var remoteDataSourceMock: RemoteDataSource
    @Mock lateinit var localDataSourceMock: LocalDataSource
    @Mock lateinit var filmMapperMock: FilmMapper
    @Mock lateinit var planetMapperMock: PlanetMapper
    @Mock lateinit var personMapperMock: PersonMapper
    @Mock lateinit var specieMapperMock: SpecieMapper
    @Mock lateinit var urlToIdMapperMock: UrlToIdMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        starWarsRepository = StarWarsRepository(filmMapperMock, planetMapperMock, specieMapperMock,
            personMapperMock, urlToIdMapperMock, localDataSourceMock, remoteDataSourceMock)
    }

    @Test
    fun getPersonsCompletes() {
        stubSearchPerson(Observable.just(listOf(MapperFactory.makePersonEntity())))
        stubPersonMapper(MapperFactory.makePerson())

        val testObserver = starWarsRepository.getPersons(MapperFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPersonReturnsData() {
        val data = listOf(MapperFactory.makePersonEntity())
        stubSearchPerson(Observable.just(data))
        val personData = MapperFactory.makePerson()
        stubPersonMapper(personData)

        val testObserver = starWarsRepository.getPersons(MapperFactory.randomString()).test()
        testObserver.assertValue(listOf(personData))
    }

    @Test
    fun getPersonCallsRemoteDataSourceWithCorrectParams() {

        val captor = argumentCaptor<String>()
        val data = listOf(MapperFactory.makePersonEntity(),
            MapperFactory.makePersonEntity(), MapperFactory.makePersonEntity())
        stubSearchPerson(Observable.just(data))

        val personData = MapperFactory.makePerson()
        stubPersonMapper(personData)

        val stubbedQuery = MapperFactory.randomString()

        starWarsRepository.getPersons(stubbedQuery)

        verify(remoteDataSourceMock).searchPersons(captor.capture())
        assertEquals(captor.firstValue, stubbedQuery)
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



    private fun stubPersonMapper(domain : Person) {
        whenever(personMapperMock.mapFromEntity(any())).thenReturn(domain)
    }

    private fun stubPlanetMapper(domain : Planet) {
        whenever(planetMapperMock.mapFromEntity(any())).thenReturn(domain)
    }

    private fun stubSpecieMapper(domain : Specie) {
        whenever(specieMapperMock.mapFromEntity(any())).thenReturn(domain)
    }

    private fun stubFilmMapper(domain : Film) {
        whenever(filmMapperMock.mapFromEntity(any())).thenReturn(domain)
    }

    private fun stubUrlMapper(id : String) {
        whenever(urlToIdMapperMock.apply(any())).thenReturn(id)
    }

    private fun stubGetFilmWithId(local : Boolean, single: Single<FilmEntity>?) {

        if (local) {
            whenever(localDataSourceMock.getFilmWithId(any())).thenReturn(single)
        }
        else {
            whenever(localDataSourceMock.getFilmWithId(any())).thenReturn(Single.error(Exception()))
            whenever(remoteDataSourceMock.getFilmWithId(any())).thenReturn(single)
        }
    }

    private fun stubGetPlanetWithId(local : Boolean, single: Single<PlanetEntity>?) {

        if (local) {
            whenever(localDataSourceMock.getPlanetWithId(any())).thenReturn(single)
        }
        else {
            whenever(localDataSourceMock.getPlanetWithId(any())).thenReturn(Single.error(Exception()))
            whenever(remoteDataSourceMock.getPlanetWithId(any())).thenReturn(single)
        }
    }

    private fun stubGetSpecieWithId(local : Boolean, single: Single<SpecieEntity>?) {

        if (local) {
            whenever(localDataSourceMock.getSpecieWithId(any())).thenReturn(single)
        }
        else {
            whenever(localDataSourceMock.getSpecieWithId(any())).thenReturn(Single.error(Exception()))
            whenever(remoteDataSourceMock.getSpecieWithId(any())).thenReturn(single)
        }
    }

    private fun stubSearchPerson(observable: Observable<List<PersonEntity>>?) {
        whenever(remoteDataSourceMock.searchPersons(any())).thenReturn(observable)
    }
}