package com.tinytap.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.tinytap.data.source.remote.source.reddit.RemoteRedditDataSource
import com.tinytap.model.network_models.RedditResponseModel
import com.tinytap.model.ui_models.DashboardCardModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RedditRepositoryImpl @Inject constructor(
    private val remoteRedditDataSource: RemoteRedditDataSource
) : RedditRepository {
    override suspend fun getRedditModel(): NetworkResponse<RedditResponseModel, String> =
        remoteRedditDataSource.getBestFromReddit()


}



