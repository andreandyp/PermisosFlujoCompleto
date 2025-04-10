package com.andreandyp.permisosflujocompleto.core.data.local.mappers

import com.andreandyp.permisosflujocompleto.core.data.local.entities.PostEntity
import com.andreandyp.permisosflujocompleto.core.domain.models.Post
import java.time.OffsetDateTime

fun PostEntity.toPost() = Post(
    id = id,
    user = user,
    description = description,
    mediaPath = imagePath,
    likes = likes,
    creationDate = OffsetDateTime.parse(creationDate),
)

fun Post.toPostEntity() = PostEntity(
    id = id,
    user = user,
    description = description,
    imagePath = mediaPath,
    likes = likes,
    creationDate = creationDate.toString(),
)