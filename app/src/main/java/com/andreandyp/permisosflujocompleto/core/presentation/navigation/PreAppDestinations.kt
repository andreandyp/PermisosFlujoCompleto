package com.andreandyp.permisosflujocompleto.core.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface PreAppDestinations {
    @Serializable
    data object Start : PreAppDestinations

    @Serializable
    data object Main : PreAppDestinations

    @Serializable
    data class AskForPermissions(val mediaPost: String) : PreAppDestinations

    @Serializable
    data class DeniedPermissionsRationale(val mediaPost: String) : PreAppDestinations

    @Serializable
    data object DeniedPermissions : PreAppDestinations
}