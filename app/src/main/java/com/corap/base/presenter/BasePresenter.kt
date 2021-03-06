package com.corap.base.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


interface BasePresenter<V> : LifecycleObserver, CoroutineScope {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()

    fun attachView(view: V)

    fun detachView()

    var job: Job
    override val coroutineContext: CoroutineContext get() =  Dispatchers.IO + job
}