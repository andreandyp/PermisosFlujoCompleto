package com.andreandyp.permisosflujocompleto.feed.presentation.state

import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.core.domain.models.Media
import java.time.LocalDate

data class NewPostState(
    val isPhotoPickerEnabled: Boolean = false,
    val userName: String = "",
    val mediaUri: String? = null,
    val newMedia: AllowedMediaPost? = null,
    val postDescription: String = "",
    val images: Map<LocalDate, List<Media>> = mapOf(),
    val videos: Map<LocalDate, List<Media>> = mapOf(),
)
