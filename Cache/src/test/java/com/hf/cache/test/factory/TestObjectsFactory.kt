package com.hf.cache.test.factory

import com.hf.cache.model.CachedFilm
import com.hf.cache.model.CachedPlanet
import com.hf.cache.model.CachedSpecie
import com.hf.data.model.FilmEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object TestObjectsFactory {

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(1, 1000 + 1)
    }

    fun makeCachedFilm(withId : String = randomInt().toString()) : CachedFilm {
        return CachedFilm(withId, randomString(), randomString(), randomString())
    }

    fun makeCachedPlanet() : CachedPlanet {
        return CachedPlanet(randomInt().toString(), randomString(), randomString())
    }

    fun makeCachedSpecie() : CachedSpecie {
        return CachedSpecie(randomInt().toString(), randomString(), randomString(), randomString())
    }

    fun makeUrl(type: String, valid: Boolean = true, id: Int = randomInt()): String {
        return if (valid) {
            "https://swapi.co/api/$type/$id/"
        } else {
            randomString()
        }
    }

    fun makeFilmEntity(withUrl : String = makeUrl("films")): FilmEntity {
        return FilmEntity(randomString(), randomString(), randomString(), withUrl)
    }

    fun makePlanetEntity(withUrl : String = makeUrl("planets")): PlanetEntity {
        return PlanetEntity(randomString(), randomString(), withUrl)
    }

    fun makeSpecieEntity(withUrl : String = makeUrl("species")): SpecieEntity {
        return SpecieEntity(randomString(), randomString(), randomString(), withUrl)
    }
}