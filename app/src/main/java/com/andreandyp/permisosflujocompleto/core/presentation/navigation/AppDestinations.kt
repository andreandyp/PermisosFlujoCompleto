package com.andreandyp.permisosflujocompleto.core.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Feed
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.andreandyp.permisosflujocompleto.R

enum class AppDestinations(
    @StringRes val label: Int,
    val icon: ImageVector,
) {
    FEED(R.string.destination_feed, Icons.AutoMirrored.Default.Feed),
    SETTINGS(R.string.destination_settings, Icons.Default.Settings),
}
