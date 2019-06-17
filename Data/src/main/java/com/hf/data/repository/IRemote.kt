package com.hf.data.repository

import com.hf.data.model.FilmEntity
import com.hf.data.model.PersonEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import io.reactivex.Observable
import io.reactivex.Single

interface IRemote {

    fun searchPersons(query: String): Observable<List<PersonEntity>>
    fun getFilm(id: String): Single<FilmEntity>
    fun getPlanet(id: String): Single<PlanetEntity>
    fun getSpecie(id: String): Single<SpecieEntity>
}