package test

import com.hf.data.model.FilmEntity
import com.hf.data.model.PersonEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import com.hf.domain.model.Film
import com.hf.domain.model.Person
import com.hf.domain.model.Planet
import com.hf.domain.model.Specie
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object MapperFactory {

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(1, 1000 + 1)
    }

    fun randomStringList(count: Int = randomInt()): List<String> {
        val list = mutableListOf<String>()
        for (i in 0..count) {
            list.add(randomString())
        }

        return list
    }

    fun randomUrl(type: String, valid: Boolean = true, id: Int = randomInt()): String {
        return if (valid) {
            "https://swapi.co/api/$type/$id/"
        } else {
            randomString()
        }
    }

    fun makeFilmEntity(): FilmEntity {
        return FilmEntity(randomString(), randomString(), randomString(), randomUrl("films"))
    }

    fun makeFilm(): Film {
        return Film(randomString(), randomString(), randomString(), randomUrl("films"))
    }

    fun makePerson(): Person {
        return Person(
            randomString(), randomString(), randomString(),
            randomStringList(),
            randomString(),
            randomStringList(), randomString()
        )
    }

    fun makePersonEntity(): PersonEntity {
        return PersonEntity(
            randomString(), randomString(), randomString(), randomStringList(),
            randomString(), randomStringList(), randomUrl("person")
        )
    }

    fun makePlanetEntity(): PlanetEntity {
        return PlanetEntity(randomString(), randomString(), randomString())
    }

    fun makePlanet(): Planet {
        return Planet(randomString(), randomString(), randomString())
    }

    fun makeSpecieEntity(): SpecieEntity {
        return SpecieEntity(randomString(), randomString(), randomString(), randomString())
    }

    fun makeSpecie(): Specie {
        return Specie(randomString(), randomString(), randomString(), randomString())
    }


}