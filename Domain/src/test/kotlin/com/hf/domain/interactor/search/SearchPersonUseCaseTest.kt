package com.hf.domain.interactor.search

import com.hf.domain.executor.PostExecutionThread
import com.hf.domain.model.Person
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

        stubSearchPersonUseCase(Observable.just(PersonDataFactory.makePersonList(PersonDataFactory.randomInt())))
        val testObserver = searchPersonUseCase.buildUseCaseObservable(PersonDataFactory.makePersonParam()).test()

        testObserver.assertComplete()
    }

    @Test
    fun searchPersonUseCaseReturnsData() {

        val personList = PersonDataFactory.makePersonList(PersonDataFactory.randomInt())
        stubSearchPersonUseCase(Observable.just(personList))
        val testObserver = searchPersonUseCase.buildUseCaseObservable(PersonDataFactory.makePersonParam()).test()
        testObserver.assertValue(personList)
    }

    @Test(expected = IllegalArgumentException::class)
    fun searchPersonUseCaseCalledWithNoParams_errorThrown() {

        val personList = PersonDataFactory.makePersonList(PersonDataFactory.randomInt())
        stubSearchPersonUseCase(Observable.just(personList))
        searchPersonUseCase.buildUseCaseObservable()
    }

    @Test
    fun searchPersonUseCaseCalledWithParams_repositoryGetsCorrectParams() {

        val captor = argumentCaptor<String>()
        val personList = PersonDataFactory.makePersonList(PersonDataFactory.randomInt())
        stubSearchPersonUseCase(Observable.just(personList))
        val stubbedParam = PersonDataFactory.makePersonParam()

        searchPersonUseCase.buildUseCaseObservable(stubbedParam)
        verify(starWarsRepository).getPersons(captor.capture())
        assertEquals(captor.firstValue, stubbedParam.query)
    }

    private fun stubSearchPersonUseCase(observable: Observable<List<Person>>) {
        whenever(starWarsRepository.getPersons(any()))
            .thenReturn(observable)
    }

}