package com.tinytap.di.providing

import android.app.Application
import androidx.room.Room
import com.tinytap.core.constants.DatabaseConstants.REDDIT_DATABASE
import com.tinytap.database.RedditDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRedditDatabase(app: Application): RedditDatabase {
        return Room
            .databaseBuilder(app, RedditDatabase::class.java, REDDIT_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }
}