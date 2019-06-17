package com.hf.domain.interactor.details

import com.hf.domain.executor.PostExecutionThread
import com.hf.domain.model.Planet
import com.hf.domain.model.Specie
import com.hf.domain.repository.IStarWarsRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import test.PlanetDataFactory
import test.SpecieDataFactory

class GetSpeciesUseCaseTest {


    private lateinit var getSpeciesUseCase: GetSpeciesUseCase
    @Mock
    lateinit var starWarsRepository: IStarWarsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getSpeciesUseCase = GetSpeciesUseCase(starWarsRepository, postExecutionThread)
    }

    @Test
    fun getSpecieUseCaseCompletes() {

        stubGetSpecieUseCase(Single.just(SpecieDataFactory.makeSpecie()))
        val testObserver = getSpeciesUseCase.buildUseCaseSingle(SpecieDataFactory.makeSpecieParam()).test()

        testObserver.assertComplete()
    }

    @Test
    fun getSpecieUseCaseReturnsData() {

        val specie = SpecieDataFactory.makeSpecie()
        stubGetSpecieUseCase(Single.just(specie))
        val testObserver = getSpeciesUseCase.buildUseCaseSingle(SpecieDataFactory.makeSpecieParam()).test()
        testObserver.assertValue(specie)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getPlantUseCaseCalledWithNoParams_errorThrown() {

        val specie = SpecieDataFactory.makeSpecie()
        stubGetSpecieUseCase(Single.just(specie))
        getSpeciesUseCase.buildUseCaseSingle()
    }

    @Test
    fun getPlantUseCaseCalledWithParams_repositoryGetsCorrectParams() {

        val captor = argumentCaptor<String>()
        stubGetSpecieUseCase(Single.just(SpecieDataFactory.makeSpecie()))
        val stubbedParam = SpecieDataFactory.makeSpecieParam()

        getSpeciesUseCase.buildUseCaseSingle(stubbedParam)
        verify(starWarsRepository).getSpecieById(captor.capture())
        assertEquals(captor.firstValue, stubbedParam.id)
    }

    private fun stubGetSpecieUseCase(single : Single<Specie>) {
        whenever(starWarsRepository.getSpecieById(any()))
            .thenReturn(single)
    }
}