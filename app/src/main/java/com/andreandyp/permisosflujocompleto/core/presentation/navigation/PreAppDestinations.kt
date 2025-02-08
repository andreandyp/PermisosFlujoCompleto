package com.andreandyp.permisosflujocompleto.core.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface PreAppDestinations {
    @Serializable
    data object Start : PreAppDestinations

    @Serializable
    data object Main : PreAppDestinations
}