package com.hf.domain.interactor.search

import com.hf.domain.executor.PostExecutionThread
import com.hf.domain.model.SearchResult
import com.hf.domain.repository.IStarWarsRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import test.PersonDataFactory

class SearchPersonUseCaseTest {


    private lateinit var searchPersonUseCase: SearchPersonUseCase
    @Mock
    lateinit var starWarsRepository: IStarWarsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchPersonUseCase = SearchPersonUseCase(starWarsRepository, postExecutionThread)
    }

    @Test
    fun searchPersonUseCaseCompletes() {

        stubSearchPersonUseCase(Observable.just(PersonDataFactory.makeSearchResult()))
        val testObserver = searchPersonUseCase.buildUseCaseObservable(PersonDataFactory.makePersonParam()).test()

        testObserver.assertComplete()
    }

    @Test
    fun searchPersonUseCaseReturnsData() {

        val searchResult = PersonDataFactory.makeSearchResult()
        stubSearchPersonUseCase(Observable.just(searchResult))
        val testObserver = searchPersonUseCase.buildUseCaseObservable(PersonDataFactory.makePersonParam()).test()
        testObserver.assertValue(searchResult)
    }

    @Test(expected = IllegalArgumentException::class)
    fun searchPersonUseCaseCalledWithNoParams_errorThrown() {

        val searchResult = PersonDataFactory.makeSearchResult()
        stubSearchPersonUseCase(Observable.just(searchResult))
        searchPersonUseCase.buildUseCaseObservable()
    }

    @Test
    fun searchPersonUseCaseCalledWithParams_repositoryGetsCorrectParams() {

        val queryCaptor = argumentCaptor<String>()
        val pageCaptor = argumentCaptor<Int>()
        val searchResult = PersonDataFactory.makeSearchResult()
        stubSearchPersonUseCase(Observable.just(searchResult))
        val stubbedParam = PersonDataFactory.makePersonParam()

        searchPersonUseCase.buildUseCaseObservable(stubbedParam)
        verify(starWarsRepository).getPersons(queryCaptor.capture(), pageCaptor.capture())
        assertEquals(queryCaptor.firstValue, stubbedParam.query)
        assertEquals(pageCaptor.firstValue, stubbedParam.page)
    }

    private fun stubSearchPersonUseCase(observable: Observable<SearchResult>) {
        whenever(starWarsRepository.getPersons(any(), any()))
            .thenReturn(observable)
    }

}