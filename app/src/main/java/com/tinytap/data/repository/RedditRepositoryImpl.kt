package com.tinytap.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.tinytap.data.source.local.source.RedditLocalDataSource
import com.tinytap.data.source.remote.source.reddit.RemoteRedditDataSource
import com.tinytap.model.database_entities.BestFromRedditEntity
import com.tinytap.model.ui_models.DashboardCardModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RedditRepositoryImpl @Inject constructor(
    private val remoteRedditDataSource: RemoteRedditDataSource,
    private val localRedditDataSource: RedditLocalDataSource
) : RedditRepository {

    override fun getDashboardModels() = localRedditDataSource.getAllBestFromReddit().map { entityList ->
        val dashboardCardModels = mutableListOf<DashboardCardModel>()
        entityList.forEach { entity ->
            dashboardCardModels.add(
                DashboardCardModel(
                    entity.id,
                    entity.thumbnailUrl,
                    entity.title,
                    entity.author
                )
            )
        }
        dashboardCardModels
    }


    override suspend fun getNewRedditModels() {
        val bestFromReddit = remoteRedditDataSource.getBestFromReddit()
        if (bestFromReddit is NetworkResponse.Error) return
        (bestFromReddit as NetworkResponse.Success).body.data.children.forEach { children ->
            val data = children.data
            val entity = BestFromRedditEntity(
                data.id,
                data.thumbnail,
                data.title,
                data.author
            )
            localRedditDataSource.insertBestFromRedditEntity(entity)
        }
    }

    override fun deleteEntity(entityId: String) = localRedditDataSource.deleteEntity(entityId)


}



