package com.luthtan.eye_beacon_android.domain.subscriber

import io.reactivex.subscribers.DisposableSubscriber

abstract class DefaultSubscriber<T> : DisposableSubscriber<T>() {
    override fun onComplete() {
        //no implementation
    }

    override fun onNext(data: T) {
        onSuccess(ResultState.Success(data))
    }

    override fun onError(throwable: Throwable) {
        onError(ResultState.Error(throwable))
    }

    override fun onStart() {
        onLoading(ResultState.Loading())
    }

    abstract fun onError(error: ResultState<T>)

    abstract fun onSuccess(data: ResultState<T>)

    abstract fun onLoading(loading: ResultState<T>)
}