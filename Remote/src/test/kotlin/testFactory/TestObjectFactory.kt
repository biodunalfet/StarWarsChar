package testFactory

import com.hf.data.model.FilmEntity
import com.hf.data.model.PersonEntity
import com.hf.data.model.PlanetEntity
import com.hf.data.model.SpecieEntity
import com.hf.remote.model.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object TestObjectFactory {

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(1, 1000 + 1)
    }

    fun makeFilmModel() : FilmModel {
        return FilmModel(randomString(), randomString(), randomString(), randomString())
    }

    fun makePlanetModel(): PlanetModel {
        return PlanetModel(randomString(), randomString(), randomString())
    }

    fun makeSpecieModel(): SpecieModel {
        return SpecieModel(randomString(), randomString(), randomString(), randomString())
    }

    fun makePersonModel() : PersonModel {
        return PersonModel(randomString(), randomString(), randomString(), listOf(randomString(),
            randomString(),
            randomString()), randomString(), listOf(randomString(), randomString()), randomString())
    }

    fun makePersonEntity() : PersonEntity {
        return PersonEntity(randomString(), randomString(), randomString(), listOf(randomString(), randomString()), randomString(), listOf(
            randomString(), randomString(), randomString()), randomString())
    }

    fun makeSearchPersonResponse(): SearchPersonResponseModel {
        return SearchPersonResponseModel(randomString(), listOf(makePersonModel(), makePersonModel()))
    }

    fun makeFilmEntity(): FilmEntity {
        return FilmEntity(randomString(), randomString(), randomString(), randomString())
    }

    fun makeSpecieEntity(): SpecieEntity {
        return SpecieEntity(randomString(), randomString(), randomString(), randomString())
    }

    fun makePlanetEntity(): PlanetEntity {
        return PlanetEntity(randomString(), randomString(), randomString())
    }
}