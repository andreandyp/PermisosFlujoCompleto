package com.andreandyp.permisosflujocompleto.modules

import com.andreandyp.permisosflujocompleto.core.data.repositories.PostsRepository
import com.andreandyp.permisosflujocompleto.core.data.repositories.SettingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::PostsRepository)
    singleOf(::SettingsRepository)
}