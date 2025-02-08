package com.andreandyp.permisosflujocompleto.modules

import com.andreandyp.permisosflujocompleto.settings.presentation.viewmodel.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingsViewModel)
}