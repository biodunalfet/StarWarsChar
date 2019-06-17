package test

import com.hf.domain.interactor.details.GetPlanetUseCase
import com.hf.domain.model.Planet
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object PlanetDataFactory {


    fun randomUuid() : String {
        return UUID.randomUUID().toString()
    }

    fun randomInt() : Int {
        return ThreadLocalRandom.current().nextInt(0, 20)
    }

    fun randomPlanetUrl(valid : Boolean) : String {
        return if (valid) {
            "https://swapi.co/api/planets/${randomInt()}/"
        }
        else {
            randomUuid()
        }
    }

    fun makePlanet() : Planet {
        return Planet(randomUuid(), randomUuid(), randomPlanetUrl(true))
    }

    fun makePlanetParam() : GetPlanetUseCase.Params {
        return GetPlanetUseCase.Params.withId(randomUuid())
    }
}