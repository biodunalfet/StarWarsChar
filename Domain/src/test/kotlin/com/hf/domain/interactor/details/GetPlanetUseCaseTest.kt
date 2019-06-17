package com.hf.domain.interactor.details

import com.hf.domain.executor.PostExecutionThread
import com.hf.domain.model.Planet
import com.hf.domain.repository.IStarWarsRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import test.PlanetDataFactory

class GetPlanetUseCaseTest {

    private lateinit var getPlanetUseCase: GetPlanetUseCase
    @Mock
    lateinit var starWarsRepository: IStarWarsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getPlanetUseCase = GetPlanetUseCase(starWarsRepository, postExecutionThread)
    }

    @Test
    fun getPlanetUseCaseCompletes() {

        stubGetPlanetUseCase(Single.just(PlanetDataFactory.makePlanet()))
        val testObserver = getPlanetUseCase.buildUseCaseSingle(PlanetDataFactory.makePlanetParam()).test()

        testObserver.assertComplete()
    }

    @Test
    fun getPlanetUseCaseReturnsData() {

        val planet = PlanetDataFactory.makePlanet()
        stubGetPlanetUseCase(Single.just(planet))
        val testObserver = getPlanetUseCase.buildUseCaseSingle(PlanetDataFactory.makePlanetParam()).test()
        testObserver.assertValue(planet)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getPlanetUseCaseCalledWithNoParams_errorThrown() {

        val planet = PlanetDataFactory.makePlanet()
        stubGetPlanetUseCase(Single.just(planet))
        getPlanetUseCase.buildUseCaseSingle()
    }

    @Test
    fun getPlanetUseCaseCalledWithParams_repositoryGetsCorrectParams() {

        val captor = argumentCaptor<String>()
        stubGetPlanetUseCase(Single.just(PlanetDataFactory.makePlanet()))
        val stubbedParam = PlanetDataFactory.makePlanetParam()

        getPlanetUseCase.buildUseCaseSingle(stubbedParam)
        verify(starWarsRepository).getPlanetById(captor.capture())
        assertEquals(captor.firstValue, stubbedParam.id)
    }

    private fun stubGetPlanetUseCase(single: Single<Planet>) {
        whenever(starWarsRepository.getPlanetById(any()))
            .thenReturn(single)
    }
}