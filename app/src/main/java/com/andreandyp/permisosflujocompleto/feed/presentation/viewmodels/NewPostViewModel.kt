package com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreandyp.permisosflujocompleto.core.data.repositories.MediaRepository
import com.andreandyp.permisosflujocompleto.core.data.repositories.PostsRepository
import com.andreandyp.permisosflujocompleto.core.data.repositories.SettingsRepository
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.core.domain.models.Post
import com.andreandyp.permisosflujocompleto.feed.presentation.state.NewPostEvents
import com.andreandyp.permisosflujocompleto.feed.presentation.state.NewPostState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class NewPostViewModel(
    private val postsRepository: PostsRepository,
    private val settingsRepository: SettingsRepository,
    private val mediaRepository: MediaRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(NewPostState())
    val state = _state.asStateFlow()

    private val _events = Channel<NewPostEvents>()
    val events = _events.receiveAsFlow()

    init {
        settingsRepository.preferences.onEach { preferences ->
            _state.update { it.copy(userName = preferences.userName) }
        }.launchIn(viewModelScope)
    }

    fun onBack() {
        _state.update {
            it.copy(mediaUri = null, newMedia = null, postDescription = "")
        }
    }

    fun onChangePostDescription(description: String) {
        _state.update { it.copy(postDescription = description) }
    }

    fun onUploadPost() = viewModelScope.launch {
        val user = settingsRepository.preferences.first().userName
        val newPost = Post(
            0L,
            user,
            _state.value.postDescription,
            _state.value.mediaUri,
            0,
            OffsetDateTime.now(),
        )
        postsRepository.addPost(newPost)
        mediaRepository.saveMediaPost(newPost)
        _state.update {
            it.copy(postDescription = "", mediaUri = null)
        }
        _events.send(NewPostEvents.GoBack)
    }

    fun onAddMedia() = viewModelScope.launch {
        val images = mediaRepository.getImages()
        val videos = mediaRepository.getVideos()
        _state.update {
            it.copy(images = images, videos = videos)
        }
    }

    fun onNewMedia(uri: String, mediaType: AllowedMediaPost) {
        _state.update { it.copy(mediaUri = uri, newMedia = mediaType) }
    }
}