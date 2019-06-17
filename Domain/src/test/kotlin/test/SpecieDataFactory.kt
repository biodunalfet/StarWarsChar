package test

import com.hf.domain.interactor.details.GetSpeciesUseCase
import com.hf.domain.model.Planet
import com.hf.domain.model.Specie
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object SpecieDataFactory {

    fun randomUuid() : String {
        return UUID.randomUUID().toString()
    }

    fun randomInt() : Int {
        return ThreadLocalRandom.current().nextInt(0, 20)
    }

    fun randomSpecieUrl(valid : Boolean = true) : String {
        return if (valid) {
            "https://swapi.co/api/species/${randomInt()}/"
        }
        else {
            randomUuid()
        }
    }

    fun makeSpecieList(size : Int) : List<String> {
        val list = mutableListOf<String>()
        for (i in 0..size) {
            list.add(randomSpecieUrl())
        }
        return list
    }

    fun makeSpecie() : Specie {
        return Specie(randomUuid(), randomUuid(), randomUuid(), randomSpecieUrl())
    }

    fun makeSpecieParam() : GetSpeciesUseCase.Params {
        return GetSpeciesUseCase.Params.withId(randomUuid())
    }
}