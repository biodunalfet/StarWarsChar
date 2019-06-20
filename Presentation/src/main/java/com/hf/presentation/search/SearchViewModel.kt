package com.hf.presentation.search

import android.util.Log
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
        searchResultsLiveData.postValue(emptyList())
    }

    fun onPersonSelected(person: PersonListItemView) {
        navToDetailsPage.value = SingleLiveEvent(person)
    }

    fun loadData(c: Int) {
        if (!isLoadingMoreFor(c + 1) && !isLastPage()) {
            Log.d("paginator", "ok, let me load more for page ${c + 1}?")
            search(currentQuery, c + 1)
            currentPage = c + 1
            Log.d("paginator", "our new current page is ${c + 1}?")
        }
    }


    fun isLastPage(): Boolean {
        return next == null
    }

    private fun isLoadingMoreFor(c: Int): Boolean {
        Log.d("paginator", "are you loading more for page $currentPage?")
        val isLoading = c == this.currentPage && loadingSearchResultsLiveData.value == true
        Log.d("paginator", if (isLoading) "yes" else "no")
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

                searchResultsLiveData.postValue(currentList)


            }
        }

        override fun onError(e: Throwable) {
            msgSearchResultsLiveData.postValue(e.localizedMessage)
        }

    }


}