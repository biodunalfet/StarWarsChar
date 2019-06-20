package test

import com.hf.domain.interactor.search.SearchPersonUseCase
import com.hf.domain.model.Person
import com.hf.domain.model.SearchResult
import com.nhaarman.mockito_kotlin.any
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object PersonDataFactory {

    fun randomUuid(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(1, 20)
    }

    fun randomUrl(type: String, valid: Boolean = true): String {
        return if (valid) {
            "https://swapi.co/api/${type}/${randomInt()}/"
        } else {
            randomUuid()
        }
    }

    fun makePersonList(size: Int): List<Person> {
        val list = mutableListOf<Person>()
        for (i in 0..size) {
            list.add(makePerson())
        }
        return list
    }

    fun makeSearchResult() : SearchResult {
        return SearchResult(randomUrl("people"), randomUrl("people"), randomInt(), makePersonList(2), randomUuid())
    }

    fun makePerson(): Person {
        return Person(
            randomUuid(), randomUuid(), randomUuid(),
            SpecieDataFactory.makeSpecieList(randomInt()),
            randomUrl("planets"),
            FilmDataFactory.makeFilmList(randomInt()), randomUrl("people")
        )
    }

    fun makePersonParam(): SearchPersonUseCase.Params {
        return SearchPersonUseCase.Params.withQuery(randomUuid(), randomInt())
    }
}