package com.hf.data.source

import com.hf.data.model.FilmEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SearchResultsEntity
import com.hf.data.model.SpecieEntity
import com.hf.data.repository.IRemote
import com.hf.data.repository.IStarWarsDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val remote: IRemote
) : IStarWarsDataSource {

    override fun searchPersons(query: String, page: Int): Observable<SearchResultsEntity> {
        return remote.searchPersons(query, page)
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