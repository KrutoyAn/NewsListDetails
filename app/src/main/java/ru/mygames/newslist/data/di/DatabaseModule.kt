package ru.mygames.newslist.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.mygames.newslist.data.local.NewsDao
import ru.mygames.newslist.data.local.NewsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "prostore_db"
        ).build()
    }

    @Provides
    fun provideNewsDao(database: NewsDatabase): NewsDao = database.newsDao()
}