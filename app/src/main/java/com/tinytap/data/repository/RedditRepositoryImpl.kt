package com.tinytap.data.repository

import android.content.Context
import com.haroldadmin.cnradapter.NetworkResponse
import com.tinytap.R
import com.tinytap.data.source.local.source.RedditLocalDataSource
import com.tinytap.data.source.remote.source.reddit.RemoteRedditDataSource
import com.tinytap.model.database_entities.BestFromRedditEntity
import com.tinytap.model.ui_models.DashboardCardModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RedditRepositoryImpl @Inject constructor(
    private val remoteRedditDataSource: RemoteRedditDataSource,
    private val localRedditDataSource: RedditLocalDataSource,
    private val context: Context
) : RedditRepository {


    override val networkError: MutableStateFlow<String> = MutableStateFlow("")

    override fun getDashboardModels() = localRedditDataSource.getAllBestFromReddit().map { entityList ->
        val dashboardCardModels = mutableListOf<DashboardCardModel>()
        entityList.forEach { entity ->
            dashboardCardModels.add(
                DashboardCardModel(
                    entity.id,
                    entity.thumbnailUrl,
                    entity.title,
                    entity.author,
                    entity.isCurrentPostOfInterest
                )
            )
        }
        dashboardCardModels
    }


    override suspend fun getNewRedditModels() {
        val bestFromReddit = remoteRedditDataSource.getBestFromReddit()
        if (bestFromReddit is NetworkResponse.Error) {
            networkError.emit(bestFromReddit.error.localizedMessage ?: context.getString(R.string.general_server_error))
            return
        }
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

    override suspend fun updatePostOfInterest(entityId: String, isCurrentlyThePostOfInterest: Boolean) =
        localRedditDataSource.updatePostOfInterest(entityId, isCurrentlyThePostOfInterest)

    override fun deleteEntity(entityId: String) = localRedditDataSource.deleteEntity(entityId)


}



