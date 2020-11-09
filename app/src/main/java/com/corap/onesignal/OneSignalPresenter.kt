package com.corap.onesignal

import androidx.lifecycle.LifecycleOwner
import com.corap.BaseApplication
import com.corap.base.presenter.BasePresenter
import com.corap.data.remote.services.APIService
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


/**
 * Created by dodydmw19 on 6/12/19.
 */

class OneSignalPresenter : BasePresenter<OneSignalView> {

    @Inject
    lateinit var apiService: APIService
    private var mvpView: OneSignalView? = null
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()

    init {
        BaseApplication.applicationComponent.inject(this)
    }

    fun registerPlayerId(playerId: String){
       // Register Player id to server

    }

    override fun onDestroy() {
        detachView()
    }

    override fun attachView(view: OneSignalView) {
        mvpView = view
        // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
        if (mvpView is LifecycleOwner) {
            (mvpView as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    override fun detachView() {
        mvpView = null
        mCompositeDisposable.let { mCompositeDisposable?.clear() }
    }


}