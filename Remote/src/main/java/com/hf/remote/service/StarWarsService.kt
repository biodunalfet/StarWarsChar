package com.hf.remote.service

import com.hf.remote.model.FilmModel
import com.hf.remote.model.PlanetModel
import com.hf.remote.model.SearchPersonResponseModel
import com.hf.remote.model.SpecieModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsService {

    @GET("/api/people/")
    fun searchPeople(@Query("search") query : String, @Query("page") page : String = "1")
        : Observable<SearchPersonResponseModel>

    @GET("/api/films/{filmId}")
    fun findFilmById(@Path("filmId") id : String)
            : Single<FilmModel>

    @GET("/api/species/{specieId}")
    fun findSpecieById(@Path("specieId") id : String)
            : Single<SpecieModel>

    @GET("/api/planets/{planetId}")
    fun findPlanetById(@Path("planetId") id : String)
            : Single<PlanetModel>

}