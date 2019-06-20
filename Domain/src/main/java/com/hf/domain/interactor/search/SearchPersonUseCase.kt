package com.hf.domain.interactor.search

import com.hf.domain.executor.PostExecutionThread
import com.hf.domain.interactor.ObservableUseCase
import com.hf.domain.interactor.search.SearchPersonUseCase.Params
import com.hf.domain.model.SearchResult
import com.hf.domain.repository.IStarWarsRepository
import io.reactivex.Observable
import javax.inject.Inject

class SearchPersonUseCase @Inject constructor(
    private val repository: IStarWarsRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<SearchResult, Params>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<SearchResult> {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return repository.getPersons(params.query, params.page)
    }

    data class Params constructor(val query: String, var page: Int = 1) {
        companion object {
            fun withQuery(query: String, page: Int): Params {
                return Params(query, page)
            }
        }//for external class to instantiate class
    }
}