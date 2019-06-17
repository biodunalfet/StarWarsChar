package com.hf.data.source

import com.hf.data.model.FilmEntity
import com.hf.data.model.PersonEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import com.hf.data.repository.IRemote
import com.hf.data.repository.IStarWarsDataSource
import com.hf.domain.model.Film
import com.hf.domain.model.Person
import com.hf.domain.model.Planet
import com.hf.domain.model.Specie
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.UnsupportedOperationException
import javax.inject.Inject

class RemoteDataSource @Inject constructor (
    private val remote : IRemote
) : IStarWarsDataSource{

    override fun searchPersons(query: String): Observable<List<PersonEntity>> {
        return remote.searchPersons(query)
    }

    override fun saveSpecie(id: String, specie: SpecieEntity): Completable {
        throw UnsupportedOperationException("not supported")
    }

    override fun savePlanet(id: String, planet: PlanetEntity): Completable {
        throw UnsupportedOperationException("not supported")
    }

    override fun saveFilm(id: String, film: FilmEntity): Completable {
        throw UnsupportedOperationException("not supported")
    }

    override fun getFilmWithId(id: String): Single<FilmEntity> {
        return remote.getFilm(id)
    }

    override fun getPlanetWithId(id: String): Single<PlanetEntity> {
        return remote.getPlanet(id)
    }

    override fun getSpecieWithId(id: String): Single<SpecieEntity> {
        return remote.getSpecie(id)
    }


}