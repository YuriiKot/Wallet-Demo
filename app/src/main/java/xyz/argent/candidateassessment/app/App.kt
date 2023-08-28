package xyz.argent.candidateassessment.app

import android.app.Application
import networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import xyz.argent.candidateassessment.di.tokenDomainModule
import xyz.argent.candidateassessment.di.tokenPresentationModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                networkModule,
                tokenDomainModule,
                tokenPresentationModule,
            )
        }
    }
}
