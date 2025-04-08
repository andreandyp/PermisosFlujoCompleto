package com.andreandyp.permisosflujocompleto.feed.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface ExtraPaneDestinations {
    @Serializable
    data object AskForPermissions : ExtraPaneDestinations

    @Serializable
    data object DeniedPermissions : ExtraPaneDestinations

    @Serializable
    data class DeniedPermissionsRationale(val mediaPost: String) : ExtraPaneDestinations
}