package com.andreandyp.permisosflujocompleto.settings.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface SettingsDestinations {
    @Serializable
    data object Settings : SettingsDestinations

    @Serializable
    data object UpdateName : SettingsDestinations

    @Serializable
    data object DeleteAllData : SettingsDestinations
}