package com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreandyp.permisosflujocompleto.core.ANDROID_TIMEOUT
import com.andreandyp.permisosflujocompleto.core.data.repositories.PostsRepository
import com.andreandyp.permisosflujocompleto.core.data.repositories.SettingsRepository
import com.andreandyp.permisosflujocompleto.core.domain.models.Post
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FeedViewModel(
    private val postsRepository: PostsRepository,
    settingsRepository: SettingsRepository,
) : ViewModel() {
    val posts = postsRepository.posts
    val isPhotoPickerAvailable = settingsRepository.preferences.map {
        it.photoPicker
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(ANDROID_TIMEOUT), false)

    fun onClickLike(post: Post) = viewModelScope.launch {
        postsRepository.likePost(post)
    }
}