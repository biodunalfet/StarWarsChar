package com.hf.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hf.domain.interactor.search.SearchPersonUseCase
import com.hf.domain.model.Person
import com.hf.presentation.SingleLiveEvent
import com.hf.presentation.mapper.PersonListItemViewMapper
import com.hf.presentation.model.PersonListItemView
import com.hf.presentation.state.Resource
import com.hf.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchPersonUseCase: SearchPersonUseCase,
    private val mapper: PersonListItemViewMapper
) : ViewModel() {

    val navToDetailsPage = MutableLiveData<SingleLiveEvent<PersonListItemView>>()

    val liveData
            : MutableLiveData<Resource<List<PersonListItemView>>> = MutableLiveData()


    fun search(query: String) {
        if (query.isNotEmpty()) {
            liveData.postValue(Resource(ResourceState.LOADING, null, null))
            return searchPersonUseCase.execute(PersonSearchResultsSubscriber(), SearchPersonUseCase.Params(query))
        } else {
            liveData.postValue(Resource(ResourceState.SUCCESS, emptyList(), null))
        }
    }

    fun onPersonSelected(person: PersonListItemView) {
        navToDetailsPage.value = SingleLiveEvent(person)
    }

    inner class PersonSearchResultsSubscriber : DisposableObserver<List<Person>>() {

        override fun onComplete() {}

        override fun onNext(t: List<Person>) {
            liveData.postValue(Resource(ResourceState.SUCCESS, t.map {
                mapper.mapToView(it)
            }, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }


}