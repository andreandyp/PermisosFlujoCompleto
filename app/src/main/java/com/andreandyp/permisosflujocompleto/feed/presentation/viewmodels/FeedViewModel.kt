package com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreandyp.permisosflujocompleto.core.data.repositories.PostsRepository
import com.andreandyp.permisosflujocompleto.core.domain.models.Post
import kotlinx.coroutines.launch

class FeedViewModel(
    private val postsRepository: PostsRepository,
) : ViewModel() {
    val posts = postsRepository.posts

    fun onClickLike(post: Post) = viewModelScope.launch {
        postsRepository.likePost(post)
    }
}