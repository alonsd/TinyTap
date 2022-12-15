package com.tinytap.di.binding

import com.tinytap.data.source.remote.source.reddit.RemoteRedditDataSource
import com.tinytap.data.source.remote.source.reddit.RemoteRedditDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteRedditDataSource(
        remoteRedditDataSourceImpl: RemoteRedditDataSourceImpl
    ): RemoteRedditDataSource

}