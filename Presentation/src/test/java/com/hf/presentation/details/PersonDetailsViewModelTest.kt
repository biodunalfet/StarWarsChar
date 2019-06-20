package com.hf.presentation.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hf.domain.interactor.details.GetFilmUseCase
import com.hf.domain.interactor.details.GetPlanetUseCase
import com.hf.domain.interactor.details.GetSpeciesUseCase
import com.hf.domain.model.Film
import com.hf.domain.model.Planet
import com.hf.domain.model.Specie
import com.hf.presentation.mapper.FilmViewMapper
import com.hf.presentation.mapper.PersonViewMapper
import com.hf.presentation.mapper.PlanetViewMapper
import com.hf.presentation.mapper.SpecieViewMapper
import com.hf.presentation.model.FilmView
import com.hf.presentation.model.PlanetView
import com.hf.presentation.model.SpecieView
import com.hf.presentation.state.ResourceState
import com.hf.presentation.test.factory.TestObjectFactory
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class PersonDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    lateinit var getFilmUseCaseMock: GetFilmUseCase
    @Mock
    lateinit var getPlanetUseCaseMock: GetPlanetUseCase
    @Mock
    lateinit var getSpecieUseCaseMock: GetSpeciesUseCase
    @Mock
    lateinit var filmViewMapperMock: FilmViewMapper
    @Mock
    lateinit var planetViewMapperMock: PlanetViewMapper
    @Mock
    lateinit var specieViewMapperMock: SpecieViewMapper
    @Mock
    lateinit var personViewMapperMock: PersonViewMapper
    lateinit var viewModel: PersonDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = PersonDetailsViewModel(
            getFilmUseCaseMock,
            getSpecieUseCaseMock, getPlanetUseCaseMock, filmViewMapperMock,
            planetViewMapperMock, specieViewMapperMock, personViewMapperMock
        )
    }

    //returns error
    //returns message for error

    @Test
    fun fetchFilms_useCaseExecutes() {
        val data = TestObjectFactory.makePersonListItemView()
        viewModel.onPersonReceived(data)
        verify(getFilmUseCaseMock, times(data.films!!.size)).execute(any(), any())
    }

    @Test
    fun fetchPlanets_useCaseExecutes() {
        viewModel.onPersonReceived(TestObjectFactory.makePersonListItemView())
        verify(getPlanetUseCaseMock, times(1)).execute(any(), any())
    }

    @Test
    fun fetchSpecie_useCaseExecutes() {
        val data = TestObjectFactory.makePersonListItemView()
        viewModel.onPersonReceived(data)
        verify(getSpecieUseCaseMock, times(1)).execute(any(), any())
    }

    @Test
    fun fetchFilms_returnSuccess() {

        val captor = argumentCaptor<DisposableSingleObserver<Film>>()

        val data = TestObjectFactory.makePersonListItemView()

        val films = TestObjectFactory.makeFilmList(data.films!!.size)
        val filmViews = TestObjectFactory.makeFilmViewList(data.films!!.size)


        films.forEachIndexed { index, film ->
            stubFilmViewMapper(film, filmViews[index])
        }

        viewModel.onPersonReceived(data)

        data.films!!.forEachIndexed { index, s ->
            verify(getFilmUseCaseMock).execute(captor.capture(), any())

            captor.allValues[index].onSuccess(films[index])

            assertEquals(ResourceState.SUCCESS, viewModel.filmLiveData.value?.status)
        }
    }

    @Test
    fun fetchSpecies_returnSuccess() {

        val captor = argumentCaptor<DisposableSingleObserver<Specie>>()

        val data = TestObjectFactory.makePersonListItemView()

        val species = TestObjectFactory.makeSpecieList(data.species!!.size)
        val specieViews = TestObjectFactory.makeSpecieViewList(data.species!!.size)


        species.forEachIndexed { index, specie ->
            stubSpecieViewMapper(specie, specieViews[index])
        }

        viewModel.onPersonReceived(data)

        verify(getSpecieUseCaseMock).execute(captor.capture(), any())

        captor.firstValue.onSuccess(species[0])

        assertEquals(ResourceState.SUCCESS, viewModel.specieLiveData.value?.status)

    }

    @Test
    fun fetchPlanet_returnSuccess() {

        val captor = argumentCaptor<DisposableSingleObserver<Planet>>()

        val data = TestObjectFactory.makePersonListItemView()

        val planet = TestObjectFactory.makePlanet()
        val planetView = TestObjectFactory.makePlanetView()

        stubPlanetViewMapper(planet, planetView)

        viewModel.onPersonReceived(data)

        verify(getPlanetUseCaseMock).execute(captor.capture(), any())

        captor.firstValue.onSuccess(planet)

        assertEquals(ResourceState.SUCCESS, viewModel.planetLiveData.value?.status)

    }

    @Test
    fun fetchFilms_returnData() {

        val captor = argumentCaptor<DisposableSingleObserver<Film>>()

        val data = TestObjectFactory.makePersonListItemView()

        val films = TestObjectFactory.makeFilmList(data.films!!.size)
        val filmViews = TestObjectFactory.makeFilmViewList(data.films!!.size)


        films.forEachIndexed { index, film ->
            stubFilmViewMapper(film, filmViews[index])
        }

        viewModel.onPersonReceived(data)

        data.films!!.forEachIndexed { index, s ->
            verify(getFilmUseCaseMock).execute(captor.capture(), any())

            captor.allValues[index].onSuccess(films[index])

            assertEquals(filmViews, viewModel.filmLiveData.value?.data)
        }
    }

    @Test
    fun fetchPlanet_returnData() {

        val captor = argumentCaptor<DisposableSingleObserver<Planet>>()

        val data = TestObjectFactory.makePersonListItemView()

        val planet = TestObjectFactory.makePlanet()
        val planetView = TestObjectFactory.makePlanetView()

        stubPlanetViewMapper(planet, planetView)

        viewModel.onPersonReceived(data)

        verify(getPlanetUseCaseMock).execute(captor.capture(), any())

        captor.firstValue.onSuccess(planet)

        assertEquals(planetView, viewModel.planetLiveData.value?.data)

    }

    @Test
    fun fetchSpecies_returnData() {

        val captor = argumentCaptor<DisposableSingleObserver<Specie>>()

        val data = TestObjectFactory.makePersonListItemView()

        val species = TestObjectFactory.makeSpecieList(data.species!!.size)
        val specieViews = TestObjectFactory.makeSpecieViewList(data.species!!.size)


        species.forEachIndexed { index, specie ->
            stubSpecieViewMapper(specie, specieViews[index])
        }

        viewModel.onPersonReceived(data)

        verify(getSpecieUseCaseMock).execute(captor.capture(), any())

        captor.firstValue.onSuccess(species[0])

        assertEquals(specieViews[0], viewModel.specieLiveData.value?.data)

    }

    @Test
    fun fetchFilms_returnErrorWithMessage() {

        val captor = argumentCaptor<DisposableSingleObserver<Film>>()

        val data = TestObjectFactory.makePersonListItemView()
        val errorMessage = TestObjectFactory.randomString()

        val films = TestObjectFactory.makeFilmList(data.films!!.size)
        val filmViews = TestObjectFactory.makeFilmViewList(data.films!!.size)


        films.forEachIndexed { index, film ->
            stubFilmViewMapper(film, filmViews[index])
        }

        viewModel.onPersonReceived(data)

        data.films!!.forEachIndexed { index, s ->
            verify(getFilmUseCaseMock).execute(captor.capture(), any())

            captor.allValues[index].onError(Exception(errorMessage))

            assertEquals(ResourceState.ERROR, viewModel.getFilmLiveData.value?.status)
            assertEquals(errorMessage, viewModel.getFilmLiveData.value?.message)
        }
    }

    @Test
    fun fetchPlanet_returnErrorWithMessage() {

        val captor = argumentCaptor<DisposableSingleObserver<Planet>>()

        val data = TestObjectFactory.makePersonListItemView()
        val errorMessage = TestObjectFactory.randomString()

        val planet = TestObjectFactory.makePlanet()
        val planetView = TestObjectFactory.makePlanetView()

        stubPlanetViewMapper(planet, planetView)

        viewModel.onPersonReceived(data)

        verify(getPlanetUseCaseMock).execute(captor.capture(), any())

        captor.firstValue.onError(Exception(errorMessage))

        assertEquals(ResourceState.ERROR, viewModel.planetLiveData.value?.status)
        assertEquals(errorMessage, viewModel.planetLiveData.value?.message)

    }

    @Test
    fun fetchSpecies_returnError() {

        val captor = argumentCaptor<DisposableSingleObserver<Specie>>()

        val data = TestObjectFactory.makePersonListItemView()
        val errorMessage = TestObjectFactory.randomString()

        val species = TestObjectFactory.makeSpecieList(data.species!!.size)
        val specieViews = TestObjectFactory.makeSpecieViewList(data.species!!.size)


        species.forEachIndexed { index, specie ->
            stubSpecieViewMapper(specie, specieViews[index])
        }

        viewModel.onPersonReceived(data)

        verify(getSpecieUseCaseMock).execute(captor.capture(), any())

        captor.firstValue.onError(Exception(errorMessage))

        assertEquals(ResourceState.ERROR, viewModel.specieLiveData.value?.status)
        assertEquals(errorMessage, viewModel.specieLiveData.value?.message)

    }

    private fun stubFilmViewMapper(film: Film, mapped: FilmView) {
        whenever(filmViewMapperMock.mapToView(film)).thenReturn(mapped)
    }

    private fun stubPlanetViewMapper(planet: Planet, mapped: PlanetView) {
        whenever(planetViewMapperMock.mapToView(planet)).thenReturn(mapped)
    }

    private fun stubSpecieViewMapper(specie: Specie, mapped: SpecieView) {
        whenever(specieViewMapperMock.mapToView(specie)).thenReturn(mapped)
    }
}