package com.hf.data.repository

import com.hf.data.model.FilmEntity
import com.hf.data.model.PersonEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IStarWarsDataSource {

    fun searchPersons(query: String): Observable<List<PersonEntity>>
    fun saveSpecie(id: String, specie: SpecieEntity): Completable
    fun savePlanet(id: String, planet: PlanetEntity): Completable
    fun saveFilm(id: String, film: FilmEntity): Completable
    fun getFilmWithId(id: String): Single<FilmEntity>
    fun getPlanetWithId(id: String): Single<PlanetEntity>
    fun getSpecieWithId(id: String): Single<SpecieEntity>

}