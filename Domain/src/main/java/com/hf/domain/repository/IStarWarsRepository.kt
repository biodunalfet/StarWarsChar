package com.hf.domain.repository

import com.hf.domain.model.Film
import com.hf.domain.model.Person
import com.hf.domain.model.Planet
import com.hf.domain.model.Specie
import io.reactivex.Observable
import io.reactivex.Single

interface IStarWarsRepository {

    fun getPersons(query : String) : Observable<List<Person>>
    fun getFilmById(url : String) : Single<Film>
    fun getSpecieById(url : String) : Single<Specie>
    fun getPlanetById(url : String) : Single<Planet>
}