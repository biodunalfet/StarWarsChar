package com.hf.domain.interactor.details

import com.hf.domain.executor.PostExecutionThread
import com.hf.domain.model.Film
import com.hf.domain.repository.IStarWarsRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import test.FilmDataFactory

class GetFilmUseCaseTest {

    private lateinit var getFilmUseCase: GetFilmUseCase
    @Mock
    lateinit var starWarsRepository: IStarWarsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getFilmUseCase = GetFilmUseCase(starWarsRepository, postExecutionThread)
    }

    @Test
    fun getFilmUseCaseCompletes() {

        stubGetFilmUseCase(Single.just(FilmDataFactory.makeFilm()))
        val testObserver = getFilmUseCase.buildUseCaseSingle(FilmDataFactory.makeFilmParam()).test()

        testObserver.assertComplete()
    }

    @Test
    fun getFilmUseCaseReturnsData() {

        val film = FilmDataFactory.makeFilm()
        stubGetFilmUseCase(Single.just(film))
        val testObserver = getFilmUseCase.buildUseCaseSingle(FilmDataFactory.makeFilmParam()).test()
        testObserver.assertValue(film)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getFilmUseCaseCalledWithNoParams_errorThrown() {

        val film = FilmDataFactory.makeFilm()
        stubGetFilmUseCase(Single.just(film))
        getFilmUseCase.buildUseCaseSingle()
    }

    @Test
    fun getFilmUSeCaseCalledWithParams_repositoryGetsCorrectParams() {

        val captor = argumentCaptor<String>()
        stubGetFilmUseCase(Single.just(FilmDataFactory.makeFilm()))
        val stubbedParam = FilmDataFactory.makeFilmParam()

        getFilmUseCase.buildUseCaseSingle(stubbedParam)
        verify(starWarsRepository).getFilmById(captor.capture())
        assertEquals(captor.firstValue, stubbedParam.id)
    }

    private fun stubGetFilmUseCase(single: Single<Film>) {
        whenever(starWarsRepository.getFilmById(any()))
            .thenReturn(single)
    }
}