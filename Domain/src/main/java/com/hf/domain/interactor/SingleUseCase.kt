package com.hf.domain.interactor

import com.hf.domain.executor.PostExecutionThread
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T, in Params> constructor(
    private val postExecutionThread: PostExecutionThread
) {

    private val disposables by lazy { CompositeDisposable() }

    abstract fun buildUseCaseSingle(params: Params? = null): Single<T>

    fun execute(observer: DisposableSingleObserver<T>, params: Params? = null) {
        val single = buildUseCaseSingle(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)
        addDisposable(single.subscribeWith(observer))
    }

    fun dispose() {
        disposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}