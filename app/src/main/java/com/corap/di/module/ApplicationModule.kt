package com.corap.di.module

import android.app.Application
import android.content.Context
import com.corap.data.remote.services.APIService
import com.corap.data.remote.services.BaseServiceFactory
import com.corap.di.scope.SuitCoreApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    @SuitCoreApplicationScope
    internal fun provideAPIService(): APIService {
        return BaseServiceFactory.getAPIService()
    }
}
