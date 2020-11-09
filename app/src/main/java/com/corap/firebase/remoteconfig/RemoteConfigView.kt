package com.corap.firebase.remoteconfig

import com.corap.base.presenter.MvpView

interface RemoteConfigView : MvpView {

    fun onUpdateAppNeeded(forceUpdate: Boolean, message: String?)

    fun onUpdateBaseUrlNeeded(type: String?, url: String?)

}