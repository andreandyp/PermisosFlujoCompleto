package com.andreandyp.permisosflujocompleto.settings.presentation.state

sealed interface SettingsEvent {
    data object UpdatedUserName : SettingsEvent
    data object RestoredData : SettingsEvent
    data object AllDataDeleted : SettingsEvent
}