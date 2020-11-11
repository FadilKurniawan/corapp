package com.corap.feature.login

import com.corap.BaseApplication
import com.corap.base.presenter.BasePresenter
import com.corap.data.remote.services.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by dodydmw19 on 7/18/18.
 */

class LoginPresenter : BasePresenter<LoginView>{

    @Inject
    lateinit var apiService: APIService
    private var mvpView: LoginView? = null
    override var job: Job = Job()

    init {
        BaseApplication.applicationComponent.inject(this)
    }

    fun login(){
        mvpView?.onLoginSuccess("success")
    }

    override fun onDestroy() {
    }

    override fun attachView(view: LoginView) {
        mvpView = view
    }

    override fun detachView() {
        mvpView = null
        job.cancel()
    }

    fun loginPanti(email:String,password:String) = launch(Dispatchers.Main) {
        runCatching {
//            apiService.getMembers()
        }
    }
}