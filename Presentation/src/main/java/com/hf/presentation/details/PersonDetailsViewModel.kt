package com.hf.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import com.hf.presentation.model.*
import com.hf.presentation.state.Resource
import com.hf.presentation.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class PersonDetailsViewModel @Inject constructor(
    private val filmUseCase: GetFilmUseCase,
    private val speciesUseCase: GetSpeciesUseCase,
    private val planetUseCase: GetPlanetUseCase,
    private val filmViewMapper: FilmViewMapper,
    private val planetViewMapper: PlanetViewMapper,
    private val specieViewMapper: SpecieViewMapper,
    private val personViewMapper: PersonViewMapper
) : ViewModel() {

    val specieLiveData
            : MutableLiveData<Resource<SpecieView>> = MutableLiveData()

    val planetLiveData
            : MutableLiveData<Resource<PlanetView>> = MutableLiveData()

    val personDetailsLiveData
            : MutableLiveData<Resource<PersonView>> = MutableLiveData()

    val filmLiveData
            : MutableLiveData<Resource<MutableList<FilmView>>> = MutableLiveData()

    val getFilmLiveData
            : MutableLiveData<Resource<MutableList<FilmView>>> = MutableLiveData()


    fun onPersonReceived(person: PersonListItemView?) {

        person?.let { p ->

            p.species?.let {
                fetchSpecie(it)
            }

            p.homeworld?.let {
                fetchPlanets(it)
            }

            p.films?.let {
                fetchFilms(it)
            }

            handleAvailablePersonDetails(p)

        }
    }

    private fun handleAvailablePersonDetails(p: PersonListItemView) {
        personDetailsLiveData.postValue(
            Resource(
                ResourceState.SUCCESS,
                personViewMapper.apply(p),
                null
            )
        )
    }

    private fun fetchFilms(films: List<String>) {
        getFilmLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        for (f in films) {
            filmUseCase.execute(GetFilmSubscriber(), GetFilmUseCase.Params(f))
        }
    }

    private fun fetchPlanets(planet: String) {
        planetLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        planetUseCase.execute(GetPlanetSubscriber(), GetPlanetUseCase.Params(planet))
    }

    private fun fetchSpecie(species: List<String>) {
        specieLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        for (s in species) {
            speciesUseCase.execute(GetSpecieSubscriber(), GetSpeciesUseCase.Params(s))
        }

    }

    inner class GetFilmSubscriber : DisposableSingleObserver<Film>() {

        override fun onSuccess(film: Film) {

            val films = filmLiveData.value?.data ?: mutableListOf()
            films.add(filmViewMapper.mapToView(film))

            filmLiveData.value = Resource(
                ResourceState.SUCCESS,
                films,
                null
            )

        }


        override fun onError(e: Throwable) {
            getFilmLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }

    inner class GetSpecieSubscriber : DisposableSingleObserver<Specie>() {

        override fun onSuccess(specie: Specie) {
            specieLiveData.postValue(Resource(ResourceState.SUCCESS, specieViewMapper.mapToView(specie), null))
        }


        override fun onError(e: Throwable) {
            specieLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }

    inner class GetPlanetSubscriber : DisposableSingleObserver<Planet>() {

        override fun onSuccess(planet: Planet) {
            planetLiveData.postValue(Resource(ResourceState.SUCCESS, planetViewMapper.mapToView(planet), null))
        }


        override fun onError(e: Throwable) {
            planetLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }


}