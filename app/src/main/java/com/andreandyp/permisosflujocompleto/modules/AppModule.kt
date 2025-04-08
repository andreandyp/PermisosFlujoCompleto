package com.andreandyp.permisosflujocompleto.modules

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.andreandyp.permisosflujocompleto.core.data.local.db.PermisosDatabase
import com.andreandyp.permisosflujocompleto.core.data.local.managers.MediaManager
import kotlinx.coroutines.Dispatchers
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
    single {
        MediaManager(Dispatchers.IO, androidContext().contentResolver, androidContext())
    }
}