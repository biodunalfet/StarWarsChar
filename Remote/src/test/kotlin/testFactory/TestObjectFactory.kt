package testFactory

import com.hf.data.model.*
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
        return SearchPersonResponseModel(randomString(), randomString(), randomInt(), listOf(makePersonModel(), makePersonModel()))
    }

    fun makeSearchResultsEntity(): SearchResultsEntity {
        return SearchResultsEntity(randomString(), randomString(), randomInt(), listOf(makePersonEntity(), makePersonEntity()))
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