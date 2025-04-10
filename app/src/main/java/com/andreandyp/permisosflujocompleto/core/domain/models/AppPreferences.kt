package com.andreandyp.permisosflujocompleto.core.domain.models

data class AppPreferences(
    val photoPicker: Boolean,
    val userName: String,
    val omitWelcome: Boolean,
)
