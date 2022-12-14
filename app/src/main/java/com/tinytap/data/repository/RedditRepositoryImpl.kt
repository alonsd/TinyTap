package com.tinytap.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.tinytap.data.source.remote.source.reddit.RemoteRedditDataSource
import com.tinytap.model.ui_models.DashboardCardModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RedditRepositoryImpl @Inject constructor(
    remoteRedditDataSource: RemoteRedditDataSource
) : RedditRepository {

    override val dashboardModels = remoteRedditDataSource
        .bestFromReddit.map { response ->
            if (response is NetworkResponse.Error) {
                NetworkResponse.ServerError(
                    response.error.localizedMessage,
                    code = 400
                )
            } else {
                val dashboardCards = mutableListOf<DashboardCardModel>()
                (response as NetworkResponse.Success).body.data.children.forEach { children ->
                    val data = children.data
                    dashboardCards.add(
                        DashboardCardModel(data.id, data.thumbnail, data.title, data.author)
                    )
                }
                NetworkResponse.Success(dashboardCards, code = 200)
            }
        }
}



