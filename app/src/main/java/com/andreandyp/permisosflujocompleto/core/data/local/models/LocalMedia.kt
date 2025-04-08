package com.andreandyp.permisosflujocompleto.core.data.local.models

import android.net.Uri

data class LocalMedia(
    val uri: Uri,
    val name: String,
    val size: Long,
    val mimeType: String,
    val dateAdded: Int,
)
