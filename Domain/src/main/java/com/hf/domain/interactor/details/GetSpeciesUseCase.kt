package com.hf.domain.interactor.details

import com.hf.domain.executor.PostExecutionThread
import com.hf.domain.interactor.SingleUseCase
import com.hf.domain.model.Specie
import com.hf.domain.repository.IStarWarsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetSpeciesUseCase @Inject constructor(
    private val repository: IStarWarsRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Specie, GetSpeciesUseCase.Params>(postExecutionThread) {

    override fun buildUseCaseSingle(params: Params?): Single<Specie> {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return repository.getSpecieById(params.id)
    }

    data class Params constructor(val id: String) {
        companion object {
            fun withId(id: String): Params {
                return Params(id)
            }
        }//for external class to instantiate class
    }
}