package com.hf.remote

import com.hf.data.model.FilmEntity
import com.hf.data.model.PersonEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import com.hf.data.repository.IRemote
import com.hf.remote.mapper.FilmMapper
import com.hf.remote.mapper.PlanetMapper
import com.hf.remote.mapper.SearchPersonResponseMapper
import com.hf.remote.mapper.SpecieMapper
import com.hf.remote.service.StarWarsService
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class RemoteImpl @Inject constructor(
    private val service : StarWarsService,
    private val responseMapper: SearchPersonResponseMapper,
    private val filmMapper: FilmMapper,
    private val planetMapper: PlanetMapper,
    private val specieMapper: SpecieMapper
) : IRemote {

    override fun searchPersons(query: String): Observable<List<PersonEntity>> {
        return service.searchPeople(query).map {
            responseMapper.mapFromModel(it)
        }
    }

    override fun getFilm(id: String): Single<FilmEntity> {
        return service.findFilmById(id).map {
            filmMapper.mapFromModel(it)
        }
    }

    override fun getPlanet(id: String): Single<PlanetEntity> {
        return service.findPlanetById(id).map {
            planetMapper.mapFromModel(it)
        }
    }

    override fun getSpecie(id: String): Single<SpecieEntity> {
        return service.findSpecieById(id).map {
            specieMapper.mapFromModel(it)
        }
    }


}