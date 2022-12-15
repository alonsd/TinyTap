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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBestFromRedditEntity(entity : BestFromRedditEntity)

    @Query("delete from RedditBest where :entityId = id")
    fun deleteEntity(entityId: String)
}