package com.andreandyp.permisosflujocompleto.core.domain.models

import java.time.LocalDateTime

data class Media(
    val uri: String,
    val name: String,
    val size: Long,
    val mimeType: MediaType,
    val dateAdded: LocalDateTime,
)

enum class MediaType {
    IMAGE, VIDEO
}