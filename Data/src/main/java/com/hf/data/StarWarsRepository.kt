package com.hf.data

import com.hf.data.mapper.*
import com.hf.data.source.LocalDataSource
import com.hf.data.source.RemoteDataSource
import com.hf.domain.model.Film
import com.hf.domain.model.Person
import com.hf.domain.model.Planet
import com.hf.domain.model.Specie
import com.hf.domain.repository.IStarWarsRepository
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.IllegalArgumentException
import javax.inject.Inject

class StarWarsRepository @Inject constructor(
    private val filmMapper : FilmMapper,
    private val planetMapper: PlanetMapper,
    private val specieMapper: SpecieMapper,
    private val personMapper: PersonMapper,
    private val urlToIdMapper : UrlToIdMapper,
    private val localDataStore : LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IStarWarsRepository {

    override fun getPersons(query: String): Observable<List<Person>> {
        return remoteDataSource.searchPersons(query).map {
            it.map { personMapper.mapFromEntity(it) }
        }
    }

    override fun getFilmById(url: String): Single<Film> {

        val id = urlToIdMapper.apply(url) ?: throw IllegalArgumentException("Invalid url passed")
        return localDataStore.getFilmWithId(id)
            .onErrorReturn {
                val filmFromRemote = remoteDataSource.getFilmWithId(id).blockingGet()
                localDataStore.saveFilm(id, filmFromRemote)
                filmFromRemote
            }
            .map {
                filmMapper.mapFromEntity(it)
            }
    }

    override fun getSpecieById(url: String): Single<Specie> {

        val id = urlToIdMapper.apply(url) ?: throw IllegalArgumentException("Invalid url passed")
        return localDataStore.getSpecieWithId(id)
            .onErrorReturn {
                val specieFromRemote = remoteDataSource.getSpecieWithId(id).blockingGet()
                localDataStore.saveSpecie(id, specieFromRemote)
                specieFromRemote
            }
            .map {
                specieMapper.mapFromEntity(it)
            }
    }

    override fun getPlanetById(url: String): Single<Planet> {

        val id = urlToIdMapper.apply(url) ?: throw IllegalArgumentException("Invalid url passed")
        return localDataStore.getPlanetWithId(id)
            .onErrorReturn {
                val planetFromRemote = remoteDataSource.getPlanetWithId(id).blockingGet()
                localDataStore.savePlanet(id, planetFromRemote)
                planetFromRemote
            }
            .map {
                planetMapper.mapFromEntity(it)
            }
    }
}