package com.andreandyp.permisosflujocompleto.core.data.repositories

import com.andreandyp.permisosflujocompleto.core.data.local.dao.PostsDao
import com.andreandyp.permisosflujocompleto.core.data.local.mappers.toPost
import com.andreandyp.permisosflujocompleto.core.data.local.mappers.toPostEntity
import com.andreandyp.permisosflujocompleto.core.domain.models.Post
import com.andreandyp.permisosflujocompleto.core.initialData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostsRepository(
    private val postsDao: PostsDao,
) {
    val posts: Flow<List<Post>> = postsDao.getPosts().map { posts ->
        posts.map { it.toPost() }
    }

    suspend fun addPost(post: Post) {
        postsDao.insert(post.toPostEntity())
    }

    suspend fun addInitialData() {
        postsDao.insertInitialData(initialData.map { it.toPostEntity() })
    }

    suspend fun likePost(post: Post) {
        postsDao.likePost(post.id)
    }

    suspend fun deleteAllData() {
        postsDao.deleteAll()
    }
}