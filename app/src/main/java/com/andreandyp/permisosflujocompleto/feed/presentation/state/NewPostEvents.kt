package com.andreandyp.permisosflujocompleto.feed.presentation.state

sealed interface NewPostEvents {
    data object GoBack : NewPostEvents
}