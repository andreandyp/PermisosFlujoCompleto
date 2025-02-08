package com.andreandyp.permisosflujocompleto.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreandyp.permisosflujocompleto.core.data.local.entities.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {
    @Query("SELECT * FROM posts ORDER BY creationDate DESC")
    fun getPosts(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInitialData(posts: List<PostEntity>)

    @Query("UPDATE posts SET likes = likes + 1 WHERE id = :postId")
    suspend fun likePost(postId: Long)

    @Query("DELETE FROM posts")
    suspend fun deleteAll()
}