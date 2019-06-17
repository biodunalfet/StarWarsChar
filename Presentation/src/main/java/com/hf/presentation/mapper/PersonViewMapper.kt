package com.hf.presentation.mapper

import com.hf.domain.model.Person
import com.hf.presentation.model.PersonListItemView
import com.hf.presentation.model.PersonView
import javax.inject.Inject

class PersonViewMapper  @Inject constructor()
    : ViewMapper<PersonListItemView, PersonView>{

    override fun apply(v1: PersonListItemView): PersonView {
        return PersonView(v1.name, v1.height, v1.birth_year)
    }



}