package com.tinytap.data.source.local.source

import com.tinytap.model.database_entities.BestFromRedditEntity
import kotlinx.coroutines.flow.Flow

interface RedditLocalDataSource {

    fun getAllBestFromReddit(): Flow<List<BestFromRedditEntity>>

    fun deleteEntity(entityId: String)

    suspend fun updatePostOfInterest(entityId: String, isCurrentlyThePostOfInterest : Boolean)

    suspend fun insertBestFromRedditEntity(entity: BestFromRedditEntity)
}
