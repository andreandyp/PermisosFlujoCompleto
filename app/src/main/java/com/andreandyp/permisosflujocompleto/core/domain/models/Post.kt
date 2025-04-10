package com.andreandyp.permisosflujocompleto.core.domain.models

import java.time.OffsetDateTime

data class Post(
    val id: Long,
    val user: String,
    val description: String,
    val mediaPath: String?,
    val likes: Int,
    val creationDate: OffsetDateTime,
)