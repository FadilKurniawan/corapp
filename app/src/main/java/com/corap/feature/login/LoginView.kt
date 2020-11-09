package com.corap.feature.login

import com.corap.base.presenter.MvpView


/**
 * Created by dodydmw19 on 7/18/18.
 */

interface LoginView : MvpView {

    fun onLoginSuccess(message: String?)

    fun onLoginFailed(message: String?)

}