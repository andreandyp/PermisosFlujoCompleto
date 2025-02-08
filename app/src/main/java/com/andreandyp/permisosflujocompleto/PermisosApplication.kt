package com.andreandyp.permisosflujocompleto

import android.app.Application
import com.andreandyp.permisosflujocompleto.modules.appModule
import com.andreandyp.permisosflujocompleto.modules.dataModule
import com.andreandyp.permisosflujocompleto.modules.feedModule
import com.andreandyp.permisosflujocompleto.modules.settingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PermisosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PermisosApplication)
            modules(appModule)
            modules(dataModule)
            modules(feedModule)
            modules(settingsModule)
        }
    }
}