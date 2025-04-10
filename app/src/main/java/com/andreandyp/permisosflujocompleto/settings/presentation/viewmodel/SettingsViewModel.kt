package com.andreandyp.permisosflujocompleto.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreandyp.permisosflujocompleto.core.ANDROID_TIMEOUT
import com.andreandyp.permisosflujocompleto.core.data.repositories.MediaRepository
import com.andreandyp.permisosflujocompleto.core.data.repositories.PostsRepository
import com.andreandyp.permisosflujocompleto.core.data.repositories.SettingsRepository
import com.andreandyp.permisosflujocompleto.settings.presentation.state.SettingsEvent
import com.andreandyp.permisosflujocompleto.settings.presentation.state.SettingsState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val postsRepository: PostsRepository,
    private val mediaRepository: MediaRepository,
) : ViewModel() {
    val preferences = settingsRepository.preferences.map {
        SettingsState(photoPickerEnabled = it.photoPicker, userName = it.userName)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(ANDROID_TIMEOUT), SettingsState())

    private val _events = Channel<SettingsEvent>()
    val events = _events.receiveAsFlow()

    fun onTogglePhotoPicker(isEnabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.savePhotoPickerConfig(isEnabled)
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch {
            settingsRepository.saveUserName(name)
            _events.send(SettingsEvent.UpdatedUserName)
        }
    }

    fun onRestoreData() {
        viewModelScope.launch {
            postsRepository.addInitialData()
            _events.send(SettingsEvent.RestoredData)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch {
            postsRepository.deleteAllData()
            mediaRepository.deleteAllData()
            _events.send(SettingsEvent.AllDataDeleted)
        }
    }
}