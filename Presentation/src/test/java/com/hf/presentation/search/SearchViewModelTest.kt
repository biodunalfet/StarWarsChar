package com.hf.presentation.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hf.domain.interactor.search.SearchPersonUseCase
import com.hf.domain.model.Person
import com.hf.domain.model.SearchResult
import com.hf.presentation.mapper.PersonListItemViewMapper
import com.hf.presentation.model.PersonListItemView
import com.hf.presentation.test.factory.TestObjectFactory
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableObserver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class SearchViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    lateinit var searchPersonUseCaseMock: SearchPersonUseCase
    @Mock
    lateinit var mapperMock: PersonListItemViewMapper
    lateinit var viewModel: SearchViewModel
    @Mock
    lateinit var personListItemViewListObserver: Observer<List<PersonListItemView>>
    @Mock
    lateinit var errorMsgObserver: Observer<String>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchViewModel(searchPersonUseCaseMock, mapperMock)
    }

    @Test
    fun search_QueryIsNotEmpty_UseCaseExecutes() {
        viewModel.search(TestObjectFactory.randomString(), TestObjectFactory.randomInt())

        verify(searchPersonUseCaseMock).execute(any(), any())
    }

    @Test
    fun search_QueryIsEmpty_DoesNotExecutesUseCase() {
        viewModel.search("", TestObjectFactory.randomInt())

        verifyNoMoreInteractions(searchPersonUseCaseMock)
    }

    @Test
    fun `show progress indicator when query is not empty and search is called`() {
        viewModel.search(TestObjectFactory.randomString(), TestObjectFactory.randomInt())
        assertEquals(viewModel.loadingSearchResultsLiveData.value, true)
    }

    @Test
    fun `clear list live data anytime a different query is entered`() {
        viewModel.currentQuery = TestObjectFactory.randomString()
        viewModel.search(
            TestObjectFactory.randomString(),
            TestObjectFactory.randomInt()
        )

        assertEquals(viewModel.searchResultsLiveData.value, emptyList<PersonListItemView>())
    }

    @Test
    fun searchWhenQueryIsNotEmptyAndResultBelongsToQuery_ReturnsSuccess() {

        val captor = argumentCaptor<DisposableObserver<SearchResult>>()
        val query = TestObjectFactory.randomString()
        viewModel.currentQuery = query

        viewModel.search(
            query,
            TestObjectFactory.randomInt()
        )
        val stubbedResult = TestObjectFactory.makeSearchResult(query)
        val stubbedPersonViewList = TestObjectFactory.makePersonListItemViewList(stubbedResult.results.size)

        stubbedResult.results.forEachIndexed { index, person ->
            stubMapResultToView(person, stubbedPersonViewList[index])
        }

        viewModel.searchResultsLiveData.observeForever(personListItemViewListObserver)

        verify(searchPersonUseCaseMock).execute(captor.capture(), any())
        captor.firstValue.onNext(stubbedResult)

        verify(personListItemViewListObserver).onChanged(stubbedPersonViewList)
    }

    @Test
    fun searchResultsNotUpdated_WhenResultDoesNotBelongsToQuery() {

        val captor = argumentCaptor<DisposableObserver<SearchResult>>()
        val currentQuery = TestObjectFactory.randomString()
        val anotherQuery = TestObjectFactory.randomString()
        viewModel.currentQuery = currentQuery

        viewModel.search(
            anotherQuery,
            TestObjectFactory.randomInt()
        )

        val stubbedResult = TestObjectFactory.makeSearchResult("another")
        val stubbedPersonViewList = TestObjectFactory.makePersonListItemViewList(stubbedResult.results.size)

        stubbedResult.results.forEachIndexed { index, person ->
            stubMapResultToView(person, stubbedPersonViewList[index])
        }

        viewModel.searchResultsLiveData.observeForever(personListItemViewListObserver)

        verify(searchPersonUseCaseMock).execute(captor.capture(), any())

        verify(personListItemViewListObserver).onChanged(emptyList())

        captor.firstValue.onNext(stubbedResult)

        verifyNoMoreInteractions(personListItemViewListObserver)
    }

    @Test
    fun searchCalled_errorReturned_errorMsgPassed() {

        val captor =
            argumentCaptor<DisposableObserver<SearchResult>>()
        val query = TestObjectFactory.randomString()
        val errorMessage = TestObjectFactory.randomString()
        viewModel.currentQuery = query

        viewModel.search(
            query,
            TestObjectFactory.randomInt()
        )
        val stubbedResult = TestObjectFactory.makeSearchResult(query)
        val stubbedPersonViewList = TestObjectFactory.makePersonListItemViewList(stubbedResult.results.size)

        stubbedResult.results.forEachIndexed { index, person ->
            stubMapResultToView(person, stubbedPersonViewList[index])
        }

        viewModel.msgSearchResultsLiveData.observeForever(errorMsgObserver)

        verify(searchPersonUseCaseMock).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException(errorMessage))

        verify(errorMsgObserver).onChanged(errorMessage)

    }

    @Test
    fun loadMoreCalledWithLastPage_doesNotLoadMore() {

        val lastPage = TestObjectFactory.randomInt()
        viewModel.next = null

        viewModel.loadData(lastPage)

        verifyNoMoreInteractions(searchPersonUseCaseMock)
    }

    private fun stubMapResultToView(person: Person, personView: PersonListItemView) {
        whenever(mapperMock.mapToView(person)).thenReturn(personView)
    }
}