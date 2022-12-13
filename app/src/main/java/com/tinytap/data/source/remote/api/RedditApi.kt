package com.tinytap.data.source.remote.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.tinytap.model.network_models.RedditResponseModel
import retrofit2.http.GET


interface RedditApi {

    @GET("best.json")
    suspend fun getBestFromReddit() : NetworkResponse<RedditResponseModel, String>

}