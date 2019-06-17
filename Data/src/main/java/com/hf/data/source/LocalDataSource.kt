package com.hf.data.source

import com.hf.data.model.FilmEntity
import com.hf.data.model.PersonEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import com.hf.data.repository.ICache
import com.hf.data.repository.IStarWarsDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val filmCache: ICache<String, FilmEntity>,
    private val planetCache: ICache<String, PlanetEntity>,
    private val specieCache: ICache<String, SpecieEntity>
) : IStarWarsDataSource {

    override fun getFilmWithId(id: String): Single<FilmEntity> {
        return filmCache.findItemById(id)
    }

    override fun getPlanetWithId(id: String): Single<PlanetEntity> {
        return planetCache.findItemById(id)
    }

    override fun getSpecieWithId(id: String): Single<SpecieEntity> {
        return specieCache.findItemById(id)
    }

    override fun searchPersons(query: String): Observable<List<PersonEntity>> {
        throw UnsupportedOperationException("not supported")
    }

    override fun saveSpecie(id: String, specie: SpecieEntity): Completable {
        return specieCache.saveItemWithId(id, specie)
    }

    override fun savePlanet(id: String, planet: PlanetEntity): Completable {
        return planetCache.saveItemWithId(id, planet)
    }

    override fun saveFilm(id: String, film: FilmEntity): Completable {
        return filmCache.saveItemWithId(id, film)
    }
}