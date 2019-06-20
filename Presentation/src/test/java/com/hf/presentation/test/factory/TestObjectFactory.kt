package com.hf.presentation.test.factory

import com.hf.domain.model.*
import com.hf.presentation.model.FilmView
import com.hf.presentation.model.PersonListItemView
import com.hf.presentation.model.PlanetView
import com.hf.presentation.model.SpecieView
import java.util.*
import java.util.concurrent.ThreadLocalRandom


object TestObjectFactory {

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(1, 1000 + 1)
    }

    fun randomFilmUrl(valid: Boolean = true): String {
        return if (valid) {
            "https://swapi.co/api/films/${randomInt()}/"
        } else {
            randomString()
        }
    }

    fun makeFilm(): Film {
        return Film(randomString(), randomString(), randomString(), randomFilmUrl(true))
    }

    fun makeFilmView(): FilmView {
        return FilmView(randomString(), randomString(), randomString())
    }

    fun makePerson(): Person {
        return Person(
            randomString(), randomString(), randomString(), listOf(randomString(), randomString()),
            randomString(), listOf(randomString()), randomString()
        )
    }

    fun makePersonListItemView(): PersonListItemView {
        return PersonListItemView(
            randomString(), randomString(), randomString(),
            listOf(randomString(), randomString()),
            randomString(), listOf(randomString()), randomString()
        )
    }

    fun makePlanet(): Planet {
        return Planet(randomString(), randomString(), randomString())
    }

    fun makeSpecie(): Specie {
        return Specie(randomString(), randomString(), randomString(), randomString())
    }

    fun makeSpecieView(): SpecieView {
        return SpecieView(randomString(), randomString(), randomString())
    }

    fun makeSearchResult(query: String = randomString()): SearchResult {
        return SearchResult(
            randomString(), randomString(), randomInt(),
            listOf(makePerson(), makePerson()), query
        )
    }

    fun makePersonListItemViewList(size: Int): List<PersonListItemView> {

        val items = mutableListOf<PersonListItemView>()
        for (i in 0 until size) {
            items.add(makePersonListItemView())
        }

        return items
    }

    fun makeFilmList(size: Int): List<Film> {
        val items = mutableListOf<Film>()

        for (i in 0 until size) items.add(makeFilm())

        return items
    }

    fun makeFilmViewList(size: Int): List<FilmView> {
        val items = mutableListOf<FilmView>()

        for (i in 0 until size) items.add(makeFilmView())

        return items
    }

    fun makeSpecieList(size: Int): List<Specie> {
        val items = mutableListOf<Specie>()

        for (i in 0 until size) items.add(makeSpecie())

        return items
    }

    fun makeSpecieViewList(size: Int): List<SpecieView> {
        val items = mutableListOf<SpecieView>()

        for (i in 0 until size) items.add(makeSpecieView())

        return items
    }

    fun makePlanetView(): PlanetView {
        return PlanetView(randomString(), randomString())
    }
}
