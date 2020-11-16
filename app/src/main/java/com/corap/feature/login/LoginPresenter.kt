package com.corap.feature.login

import com.corap.BaseApplication
import com.corap.base.presenter.BasePresenter
import com.corap.data.local.prefs.DataConstant
import com.corap.data.local.prefs.SuitPreferences
import com.corap.data.model.User
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
            apiService.postLogin(email,password).await()
        }.onSuccess {
            it.result.let {
                runCatching {
                    it?.token?.let { it1 -> SuitPreferences.instance()?.saveString(DataConstant.USER_TOKEN, it1) }
                    SuitPreferences.instance()?.saveString(DataConstant.USER_ID, it?.user?.id.toString())
                    SuitPreferences.instance()?.saveObject(DataConstant.USER_DATA, it?.user)
                    SuitPreferences.instance()?.saveBoolean(DataConstant.IS_LOGIN, true)
                }.onSuccess {
                    val user = SuitPreferences.instance()?.getObject(DataConstant.USER_DATA, User::class.java)
                    val login = SuitPreferences.instance()?.getBoolean(DataConstant.IS_LOGIN)
                    println("*** user: $user")
                    println("*** login: $login")
                    mvpView?.onLoginSuccess("success")
                }.onFailure {
                    mvpView?.onLoginFailed("Fail to save in cache")
                }
        }
        }.onFailure {
            mvpView?.onLoginFailed(it.message.toString())
        }
    }
}