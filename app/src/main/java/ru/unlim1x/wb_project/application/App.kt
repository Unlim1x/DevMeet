package ru.unlim1x.wb_project.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.lim1x.domain.di.domainModule
import ru.lim1x.repository.di.repositoryModule
import ru.unlim1x.wb_project.modules.appModule

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(appModule, domainModule, repositoryModule)
        }
    }
}