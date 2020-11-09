package com.corap

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.onesignal.OneSignal
import com.corap.data.local.prefs.DataConstant
import com.corap.data.local.prefs.SuitPreferences
import com.corap.di.component.ApplicationComponent
import com.corap.di.component.DaggerApplicationComponent
import com.corap.di.module.ApplicationModule
import com.corap.helper.CommonUtils
import com.corap.onesignal.NotificationOpenedHandler
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by DODYDMW19 on 1/30/2018.
 */

class BaseApplication : MultiDexApplication() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        // Initial Preferences
        SuitPreferences.init(applicationContext)

        CommonUtils.setDefaultBaseUrlIfNeeded()

        appContext = applicationContext
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        Fresco.initialize(this)

        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                //.encryptionKey(CommonUtils.getKey()) // encrypt realm
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(NotificationOpenedHandler())
                .init()
        OneSignal.enableVibrate(true)
        OneSignal.enableSound(true)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        OneSignal.idsAvailable { userId, _ ->

            if (userId != null) {
                SuitPreferences.instance()?.saveString(DataConstant.PLAYER_ID, userId)
            }
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}