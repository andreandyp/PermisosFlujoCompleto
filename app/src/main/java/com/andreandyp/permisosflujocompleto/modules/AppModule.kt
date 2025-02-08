package com.andreandyp.permisosflujocompleto.modules

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.andreandyp.permisosflujocompleto.core.data.local.db.PermisosDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            PermisosDatabase::class.java, "permisos.db"
        ).build()
    }
    single { get<PermisosDatabase>().postsDao }
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile("preferences.xml")
        }
    }
}