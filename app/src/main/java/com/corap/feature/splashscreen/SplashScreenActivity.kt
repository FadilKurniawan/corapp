package com.corap.feature.splashscreen

import android.os.Bundle
import com.corap.R
import com.corap.base.ui.BaseActivity
import com.corap.data.local.prefs.DataConstant
import com.corap.data.local.prefs.SuitPreferences
import com.corap.data.model.User
import com.corap.feature.login.LoginActivity
import com.corap.feature.main.MainActivity
import com.corap.helper.CommonConstant
import kotlinx.android.synthetic.main.activity_splashscreen.*

/**
 * Created by dodydmw19 on 12/19/18.
 */

class SplashScreenActivity : BaseActivity(), SplashScreenView {

    private var splashScreenPresenter: SplashScreenPresenter? = null
    private val actionClicked = ::dialogPositiveAction

    override val resourceLayout: Int = R.layout.activity_splashscreen

    override fun onViewReady(savedInstanceState: Bundle?) {
        changeProgressBarColor(R.color.white, progressBar)
        setupPresenter()
    }

    private fun handleIntent(){
        val data: Bundle? = intent.extras
        if(data?.getString(CommonConstant.APP_CRASH) != null){
            showConfirmationSingleDialog(getString(R.string.txt_error_crash), actionClicked)
        }else{
            splashScreenPresenter?.initialize()
        }
    }

    private fun setupPresenter() {
        splashScreenPresenter = SplashScreenPresenter()
        splashScreenPresenter?.attachView(this)
        handleIntent()
    }

    override fun navigateToMainView() {
        val user = SuitPreferences.instance()?.getObject(DataConstant.USER_DATA,User::class.java)
        val login = SuitPreferences.instance()?.getBoolean(DataConstant.IS_LOGIN)
        println("*** user: $user")
        println("*** login: $login")
        if(user == null && login == false) {
            goToActivity(LoginActivity::class.java, null, clearIntent = true, isFinish = true)
        }else{
            goToActivity(MainActivity::class.java, null, clearIntent = true, isFinish = true)
        }
    }

    private fun dialogPositiveAction() {
        splashScreenPresenter?.initialize()
    }

}