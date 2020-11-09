package com.corap.di.component

import com.corap.di.module.ApplicationModule
import com.corap.di.scope.SuitCoreApplicationScope
import com.corap.feature.login.LoginPresenter
import com.corap.feature.member.MemberPresenter
import com.corap.feature.splashscreen.SplashScreenPresenter
import com.corap.firebase.remoteconfig.RemoteConfigPresenter
import com.corap.onesignal.OneSignalPresenter
import dagger.Component

@SuitCoreApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(memberPresenter: MemberPresenter)

    fun inject(loginPresenter: LoginPresenter)

    fun inject(splashScreenPresenter: SplashScreenPresenter)

    fun inject(oneSignalPresenter: OneSignalPresenter)

    fun inject(remoteConfigPresenter: RemoteConfigPresenter)
}