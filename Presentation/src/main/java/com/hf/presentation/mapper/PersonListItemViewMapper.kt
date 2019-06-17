package com.hf.presentation.mapper

import com.hf.domain.model.Person
import com.hf.presentation.model.PersonListItemView
import javax.inject.Inject

class PersonListItemViewMapper @Inject constructor() : Mapper<PersonListItemView, Person> {

    override fun mapToView(type: Person): PersonListItemView {
        return PersonListItemView(
            type.name,
            type.height,
            type.birth_year,
            type.species,
            type.homeworld,
            type.films,
            type.url
        )
    }
}