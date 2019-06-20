package com.hf.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hf.domain.interactor.search.SearchPersonUseCase
import com.hf.domain.model.SearchResult
import com.hf.presentation.SingleLiveEvent
import com.hf.presentation.mapper.PersonListItemViewMapper
import com.hf.presentation.model.PersonListItemView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchPersonUseCase: SearchPersonUseCase,
    private val mapper: PersonListItemViewMapper
) : ViewModel() {

    val navToDetailsPage = MutableLiveData<SingleLiveEvent<PersonListItemView>>()

    val searchResultsLiveData
            : MutableLiveData<List<PersonListItemView>> = MutableLiveData()

    val loadingSearchResultsLiveData
            : MutableLiveData<Boolean> = MutableLiveData()

    val msgSearchResultsLiveData
            : MutableLiveData<String> = MutableLiveData()

    var next: String? = null
    var resultsCount: Int? = null
    var currentQuery: String = ""
    var currentPage = 1

    override fun onCleared() {
        searchPersonUseCase.dispose()
        super.onCleared()
    }

    fun search(newQuery: String, page: Int = 1) {

        if (newQuery.isNotEmpty()) {

            if (newQuery != currentQuery) {
                resetList()
            }

            loadingSearchResultsLiveData.value = true
            searchPersonUseCase.execute(PersonSearchResultsSubscriber(), SearchPersonUseCase.Params(newQuery, page))
        } else {
            resetList()
        }

        currentQuery = newQuery
    }

    private fun resetList() {
        currentPage = 1
        searchResultsLiveData.value = emptyList()
        loadingSearchResultsLiveData.value = false
    }

    fun onPersonSelected(person: PersonListItemView) {
        navToDetailsPage.value = SingleLiveEvent(person)
    }

    fun loadData(c: Int) {
        if (!isLoadingMoreFor(c + 1) && !isLastPage()) {
            search(currentQuery, c + 1)
            currentPage = c + 1
        }
    }


    fun isLastPage(): Boolean {
        return next == null
    }

    private fun isLoadingMoreFor(c: Int): Boolean {
        val isLoading = c == this.currentPage && loadingSearchResultsLiveData.value == true
        return isLoading
    }

    inner class PersonSearchResultsSubscriber : DisposableObserver<SearchResult>() {

        override fun onComplete() {}

        override fun onNext(t: SearchResult) {
            if (t.query == currentQuery) {
                loadingSearchResultsLiveData.value = false
                resultsCount = t.count
                next = t.next

                val currentList = searchResultsLiveData.value?.toMutableList() ?: mutableListOf()
                currentList.addAll(t.results.map { mapper.mapToView(it) })

                searchResultsLiveData.value = currentList


            }
        }

        override fun onError(e: Throwable) {
            msgSearchResultsLiveData.value = e.localizedMessage
        }

    }


}