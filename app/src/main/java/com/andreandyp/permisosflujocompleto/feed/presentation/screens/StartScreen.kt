package com.andreandyp.permisosflujocompleto.feed.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andreandyp.permisosflujocompleto.core.collectAsEventsWithLifecycle
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.StartLayout
import com.andreandyp.permisosflujocompleto.feed.presentation.state.StartEvents
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.StartViewModel

@Composable
fun StartScreen(
    viewModel: StartViewModel,
    onClickContinue: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    viewModel.events.collectAsEventsWithLifecycle {
        when (it) {
            StartEvents.Continue -> onClickContinue()
        }
    }

    StartLayout(
        state = state,
        onChangeUserName = viewModel::onChangeUserName,
        onClickContinue = viewModel::onClickContinue,
        onCheckOmit = viewModel::onCheckOmit,
    )
}
