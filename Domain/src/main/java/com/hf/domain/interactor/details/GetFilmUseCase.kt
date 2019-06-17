package com.hf.domain.interactor.details

import com.hf.domain.executor.PostExecutionThread
import com.hf.domain.interactor.SingleUseCase
import com.hf.domain.model.Film
import com.hf.domain.repository.IStarWarsRepository
import io.reactivex.Single
import java.lang.IllegalArgumentException
import javax.inject.Inject

class GetFilmUseCase @Inject constructor(
    private val repository: IStarWarsRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Film, GetFilmUseCase.Params>(postExecutionThread){

    override fun buildUseCaseSingle(params: Params?): Single<Film> {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return repository.getFilmById(params.id)
    }

    data class Params constructor(val id : String) {
        companion object {
            fun withId(id: String) : Params {
                return Params(id)
            }
        }//for external class to instantiate class
    }
}