package com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreandyp.permisosflujocompleto.core.data.repositories.PostsRepository
import com.andreandyp.permisosflujocompleto.core.data.repositories.SettingsRepository
import com.andreandyp.permisosflujocompleto.feed.presentation.state.StartEvents
import com.andreandyp.permisosflujocompleto.feed.presentation.state.StartState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StartViewModel(
    private val settingsRepository: SettingsRepository,
    private val postsRepository: PostsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(StartState())
    val state = _state.asStateFlow()

    private val _events = Channel<StartEvents>()
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            val settings = settingsRepository.preferences.first()
            if (settings.omitWelcome) {
                _events.send(StartEvents.Continue)
            } else {
                _state.value = StartState(userName = settings.userName, omitWelcome = true)
            }
        }
    }

    fun onChangeUserName(userName: String) {
        _state.update {
            it.copy(userName = userName)
        }
    }

    fun onCheckOmit(omitWelcome: Boolean) {
        _state.update {
            it.copy(omitWelcome = omitWelcome)
        }
    }

    fun onClickContinue() = viewModelScope.launch {
        settingsRepository.saveUserName(_state.value.userName)
        settingsRepository.saveOmitWelcome(_state.value.omitWelcome)
        postsRepository.addInitialData()
        _events.send(StartEvents.Continue)
    }
}