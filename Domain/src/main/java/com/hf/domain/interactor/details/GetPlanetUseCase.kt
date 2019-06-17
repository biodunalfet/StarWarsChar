package com.hf.domain.interactor.details

import com.hf.domain.executor.PostExecutionThread
import com.hf.domain.interactor.SingleUseCase
import com.hf.domain.model.Planet
import com.hf.domain.repository.IStarWarsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPlanetUseCase @Inject constructor(
    private val repository: IStarWarsRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Planet, GetPlanetUseCase.Params>(postExecutionThread) {

    override fun buildUseCaseSingle(params: Params?): Single<Planet> {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return repository.getPlanetById(params.id)
    }

    data class Params constructor(val id: String) {
        companion object {
            fun withId(id: String): Params {
                return Params(id)
            }
        }//for external class to instantiate class
    }
}