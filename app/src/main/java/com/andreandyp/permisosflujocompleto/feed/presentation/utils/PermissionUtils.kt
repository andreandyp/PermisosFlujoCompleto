package com.andreandyp.permisosflujocompleto.feed.presentation.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
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

fun hasMediaPermission(permissions: Map<String, Boolean>): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        permissions[Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED] == true
    } else {
        permissions.values.all { it }
    }
}

fun Activity.shouldShowCameraRationale(): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(
        this,
        Manifest.permission.CAMERA,
    )
}

fun Activity.shouldShowMediaPermissionRationale(): Boolean {
    return androidMediaPermissions.map {
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            it,
        )
    }.any { it }
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

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
