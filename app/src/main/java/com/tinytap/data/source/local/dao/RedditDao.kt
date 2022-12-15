package com.tinytap.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tinytap.model.database_entities.BestFromRedditEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RedditDao {

    @Query("select * from RedditBest")
    fun getAllBestFromReddit() : Flow<List<BestFromRedditEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBestFromRedditEntity(entity : BestFromRedditEntity)

    @Query("delete from RedditBest where :entityId = id")
    fun deleteEntity(entityId: String)

    @Query("update RedditBest set isCurrentPostOfInterest = 1 where :entityId = id ")
    suspend fun updatePostOfInterest(entityId: String)

    @Query("update RedditBest set isCurrentPostOfInterest = 0 where :entityId = id ")
    suspend fun removePostOfInterest(entityId: String)
}