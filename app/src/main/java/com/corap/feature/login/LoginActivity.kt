package com.corap.feature.login

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.corap.R
import com.corap.base.ui.BaseActivity
import com.corap.feature.main.MainActivity
import com.corap.firebase.remoteconfig.RemoteConfigHelper
import com.corap.firebase.remoteconfig.RemoteConfigPresenter
import com.corap.firebase.remoteconfig.RemoteConfigView
import com.corap.helper.CommonConstant
import com.corap.helper.CommonUtils
import com.corap.helper.permission.SuitPermissions
import com.corap.helper.socialauth.facebook.FacebookHelper
import com.corap.helper.socialauth.facebook.FacebookListener
import com.corap.helper.socialauth.google.GoogleListener
import com.corap.helper.socialauth.google.GoogleSignInHelper
import com.corap.helper.socialauth.twitter.TwitterHelper
import com.corap.helper.socialauth.twitter.TwitterListener
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by dodydmw19 on 7/18/18.
 */

class LoginActivity : BaseActivity(), LoginView, RemoteConfigView, GoogleListener, FacebookListener, TwitterListener {

    private var loginPresenter: LoginPresenter? = null
    private var remoteConfigPresenter: RemoteConfigPresenter? = null

    private var mGoogleHelper: GoogleSignInHelper? = null
    private var mTwitterHelper: TwitterHelper? = null
    private var mFbHelper: FacebookHelper? = null

    override val resourceLayout: Int = R.layout.activity_login

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupPresenter()
        setupSocialLogin()
        actionClicked()
        needPermissions()
    }

    override fun onResume() {
        super.onResume()
        remoteConfigPresenter?.checkUpdate(CommonConstant.CHECK_APP_VERSION) // check app version and notify update from remote config
        remoteConfigPresenter?.checkUpdate(CommonConstant.CHECK_BASE_URL) // check base url from remote config if any changes
    }

    private fun setupPresenter() {
        loginPresenter = LoginPresenter()
        loginPresenter?.attachView(this)
    }

    private fun needPermissions() {
        SuitPermissions.with(this)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE)
                .onAccepted {
                    for (s in it) {
                        Log.d("granted_permission", s)
                    }
                    showToast("Granted")
                }
                .onDenied {
                    showToast("Denied")
                }
                .onForeverDenied {
                    showToast("Forever denied")
                }
                .ask()
    }

    private fun setupSocialLogin() {
        // Google  initialization
        mGoogleHelper = GoogleSignInHelper(this, R.string.google_default_web_client_id, this)

        // twitter initialization
        mTwitterHelper = TwitterHelper(
                R.string.twitter_api_key,
                R.string.twitter_secret_key,
                this,
                this)

        // fb initialization
        mFbHelper = FacebookHelper(this, getString(R.string.facebook_request_field))

        signOut()
    }

    private fun signOut() {
        mGoogleHelper?.performSignOut()
        mFbHelper?.performSignOut()
    }

    override fun onLoginSuccess(message: String?) {
        goToActivity(MainActivity::class.java, null, clearIntent = true, isFinish = true)
    }

    override fun onLoginFailed(message: String?) {
        message?.let {
            showToast(message.toString())
        }
    }

    override fun onGoogleAuthSignIn(authToken: String?, userId: String?) {
        // send token & user_id to server
        loginPresenter?.login()
    }

    override fun onGoogleAuthSignInFailed(errorMessage: String?) {
        showToast(errorMessage.toString())
    }

    override fun onFbSignInFail(errorMessage: String?) {
        showToast(errorMessage.toString())
    }

    override fun onFbSignInSuccess(authToken: String?, userId: String?) {
        // send token & user_id to server
        loginPresenter?.login()
    }

    override fun onTwitterError(errorMessage: String?) {
        showToast(errorMessage.toString())
    }

    override fun onTwitterSignIn(authToken: String?, secret: String?, userId: String?) {
        // send token & user_id to server
        loginPresenter?.login()
    }

    override fun onUpdateAppNeeded(forceUpdate: Boolean, message: String?) {
        when (forceUpdate) {
            true -> {
                val confirmDialog = AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage(message)
                        .setPositiveButton("OK") { d, _ ->
                            d.dismiss()
                            CommonUtils.openAppInStore(this)
                        }
                        .create()

                confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                confirmDialog.show()
            }
            false -> {
                val confirmDialog = AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage(message)
                        .setPositiveButton("OK") { d, _ ->
                            d.dismiss()
                            CommonUtils.openAppInStore(this)
                        }
                        .setNegativeButton("CANCEL") { d, _ -> d.dismiss() }
                        .create()

                confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                confirmDialog.show()
            }
        }
    }

    override fun onUpdateBaseUrlNeeded(type: String?, url: String?) {
        RemoteConfigHelper.changeBaseUrl(this, type.toString(), url.toString())
    }

    private fun actionClicked() {
        relLogin.setOnClickListener {
            if(edtEmail.text.isEmpty() || edtPassword.text.isEmpty()){
                showAlertDialog("Email or Password cannot be empty")
            }else{
                loginPresenter?.loginPanti(edtEmail.text.toString(),edtPassword.text.toString())
            }
        }
        relGoogle.setOnClickListener {
            mGoogleHelper?.performSignIn(this)
        }

        relFacebook.setOnClickListener {
            mFbHelper?.performSignIn(this)
        }

        relTwitter.setOnClickListener {
            if (CommonUtils.checkTwitterApp(this)) {
                mTwitterHelper?.performSignIn()
            } else {
                showToast(getString(R.string.txt_twitter_not_installed))
            }
        }

        tvSkip.setOnClickListener {
            goToActivity(MainActivity::class.java, null, clearIntent = true, isFinish = true)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            mGoogleHelper?.onActivityResult(requestCode, resultCode, data)
            mTwitterHelper?.onActivityResult(requestCode, resultCode, data)
            mFbHelper?.onActivityResult(requestCode, resultCode, data)
        }
    }

}