package com.andreandyp.permisosflujocompleto.modules

import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.FeedViewModel
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.NewPostViewModel
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.StartViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val feedModule = module {
    viewModelOf(::FeedViewModel)
    viewModelOf(::StartViewModel)
    viewModelOf(::NewPostViewModel)
}