package com.andreandyp.permisosflujocompleto.feed.presentation.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

const val CAMERA_FILE_PREFIX = "photo"

fun Context.getPhotoUri(): Uri {
    return FileProvider.getUriForFile(
        this,
        "${this.applicationContext.packageName}.fileprovider",
        File.createTempFile(CAMERA_FILE_PREFIX, null, this.cacheDir),
    )
}