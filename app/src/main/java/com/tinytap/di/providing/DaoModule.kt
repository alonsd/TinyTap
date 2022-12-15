package com.tinytap.di.providing

import com.tinytap.data.source.local.dao.RedditDao
import com.tinytap.database.RedditDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideRedditDao(redditDatabase : RedditDatabase): RedditDao {
        return redditDatabase.getRedditDao()
    }
}