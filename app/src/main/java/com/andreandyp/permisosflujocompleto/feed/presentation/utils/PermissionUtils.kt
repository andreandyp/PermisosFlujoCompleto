package com.andreandyp.permisosflujocompleto.feed.presentation.utils

import android.Manifest
import android.os.Build
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.isGranted

val androidMediaPermissions = buildList {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        add(Manifest.permission.READ_MEDIA_IMAGES)
        add(Manifest.permission.READ_MEDIA_VIDEO)
        add(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        add(Manifest.permission.READ_MEDIA_IMAGES)
        add(Manifest.permission.READ_MEDIA_VIDEO)
    } else {
        add(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
val MultiplePermissionsState.hasPartialAccessMediaPermission: Boolean
    get() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            return false
        }

        val hasVisualUserSelected =
            permissions.first { it.permission == Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED }.status.isGranted
        val hasImagesPermission =
            permissions.first { it.permission == Manifest.permission.READ_MEDIA_IMAGES }.status.isGranted
        val hasVideoPermission =
            permissions.first { it.permission == Manifest.permission.READ_MEDIA_VIDEO }.status.isGranted

        return hasVisualUserSelected && !hasImagesPermission && !hasVideoPermission
    }
