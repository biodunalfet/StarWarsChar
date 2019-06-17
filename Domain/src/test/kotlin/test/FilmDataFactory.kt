package test

import com.hf.domain.interactor.details.GetFilmUseCase
import com.hf.domain.model.Film
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object FilmDataFactory {

    fun randomUuid() : String {
        return UUID.randomUUID().toString()
    }

    fun randomInt() : Int {
        return ThreadLocalRandom.current().nextInt(0, 20)
    }

    fun randomFilmUrl(valid : Boolean = true) : String {
        return if (valid) {
            "https://swapi.co/api/films/${randomInt()}/"
        }
        else {
            randomUuid()
        }
    }

    fun makeFilmList(size : Int) : List<String> {
        val list = mutableListOf<String>()
        for (i in 0..size) {
            list.add(randomFilmUrl())
        }
        return list
    }

    fun makeFilm() : Film {
        return Film(randomUuid(), randomUuid(), randomUuid(), randomFilmUrl(true))
    }

    fun makeFilmParam() : GetFilmUseCase.Params {
        return GetFilmUseCase.Params.withId(randomUuid())
    }



}