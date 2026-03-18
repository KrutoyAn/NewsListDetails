package ru.mygames.newslist.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mygames.newslist.data.repository.NewsRepositoryImpl
import ru.mygames.newslist.domain.repository.NewsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        impl: NewsRepositoryImpl
    ): NewsRepository
}