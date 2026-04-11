package com.gblrod.agentsvault.core

import android.app.Application
import com.gblrod.agentsvault.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class AgentsVaultApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@AgentsVaultApplication)
            modules(
                appModules
            )
        }
    }
}