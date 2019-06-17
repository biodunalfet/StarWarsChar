package com.hf.domain.interactor.search

import com.hf.domain.executor.PostExecutionThread
import com.hf.domain.interactor.ObservableUseCase
import com.hf.domain.interactor.search.SearchPersonUseCase.*
import com.hf.domain.model.Person
import com.hf.domain.repository.IStarWarsRepository
import io.reactivex.Observable
import java.lang.IllegalArgumentException
import javax.inject.Inject

class SearchPersonUseCase @Inject constructor(
    private val repository: IStarWarsRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Person>, Params>(postExecutionThread){

    override fun buildUseCaseObservable(params: Params?): Observable<List<Person>> {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return repository.getPersons(params.query)
    }

    data class Params constructor(val query : String) {
        companion object {
            fun withQuery(query: String) : Params {
                return Params(query)
            }
        }//for external class to instantiate class
    }
}