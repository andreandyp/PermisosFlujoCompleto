package com.andreandyp.permisosflujocompleto.feed.presentation.state

import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost

data class NewPostState(
    val userName: String = "",
    val mediaUri: String? = null,
    val newMedia: AllowedMediaPost? = null,
    val postDescription: String = "",
)
