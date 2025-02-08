package com.andreandyp.permisosflujocompleto.feed.presentation.state

sealed interface StartEvents {
    data object Continue : StartEvents
}