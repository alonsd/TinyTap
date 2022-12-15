package com.tinytap.data.source.local.source

import com.tinytap.data.source.local.dao.RedditDao
import com.tinytap.model.database_entities.BestFromRedditEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RedditLocalDataSourceImpl @Inject constructor(
    private val redditDao: RedditDao
) : RedditLocalDataSource {

    override suspend fun insertBestFromRedditEntity(entity: BestFromRedditEntity) =
        redditDao.insertBestFromRedditEntity(entity)

    override fun getAllBestFromReddit(): Flow<List<BestFromRedditEntity>> = redditDao.getAllBestFromReddit()

    override fun deleteEntity(entityId: String) = redditDao.deleteEntity(entityId)

}