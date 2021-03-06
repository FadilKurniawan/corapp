package com.corap.feature.splashscreen

import android.os.Handler
import com.corap.BaseApplication
import com.corap.base.presenter.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by dodydmw19 on 12/19/18.
 */

class SplashScreenPresenter : BasePresenter<SplashScreenView> {

    private var mvpView: SplashScreenView? = null
    private val time: Long = 3000

    override var job: Job = Job()

    init {
        BaseApplication.applicationComponent.inject(this)
    }

    override fun onDestroy() {
        detachView()
    }

    fun initialize() {
        Handler().postDelayed({ mvpView?.navigateToMainView() }, time)
    }

    override fun attachView(view: SplashScreenView) {
        mvpView = view
    }

    override fun detachView() {
        mvpView = null
        job.cancel()
    }
}