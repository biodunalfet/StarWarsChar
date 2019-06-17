package com.hf.data.mapper

import com.hf.data.model.PersonEntity
import com.hf.domain.model.Person
import javax.inject.Inject

class PersonMapper @Inject constructor()
    : EntityMapper<PersonEntity, Person> {

    override fun mapFromEntity(entity: PersonEntity): Person {
        return Person(entity.name,
            entity.height,
            entity.birth_year,
            entity.species,
            entity.homeworld,
            entity.films,
            entity.url
            )
    }

    override fun mapToEntity(domain: Person): PersonEntity {
        return PersonEntity(
            domain.name,
            domain.height,
            domain.birth_year,
            domain.species,
            domain.homeworld,
            domain.films,
            domain.url
        )
    }
}