package com.tinytap.data.repository

import com.tinytap.data.source.remote.source.reddit.RemoteRedditDataSource
import javax.inject.Inject

class RedditRepositoryImpl @Inject constructor(
    private val remoteRedditDataSource: RemoteRedditDataSource
) : RedditRepository {

}

