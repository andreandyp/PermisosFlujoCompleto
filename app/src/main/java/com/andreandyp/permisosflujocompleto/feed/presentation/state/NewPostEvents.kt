package com.andreandyp.permisosflujocompleto.feed.presentation.state

sealed interface NewPostEvents {
    data object GoBack : NewPostEvents
    data object LaunchCamera : NewPostEvents
    data object ShowAppPicker : NewPostEvents
    data object ShowAndroidPicker : NewPostEvents
}