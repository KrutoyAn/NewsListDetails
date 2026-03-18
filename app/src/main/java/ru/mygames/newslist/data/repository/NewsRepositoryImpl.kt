package ru.mygames.newslist.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mygames.newslist.data.local.NewsDao
import ru.mygames.newslist.data.mapper.NewsMapper.toDomain
import ru.mygames.newslist.data.mapper.NewsMapper.toEntity
import ru.mygames.newslist.data.remote.NewsApi
import ru.mygames.newslist.domain.model.News
import ru.mygames.newslist.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val dao: NewsDao
) : NewsRepository {

    override fun getAllNews(): Flow<List<News>> = dao.getAllNews()
        .map { entities -> entities.map { it.toDomain() } }

    override fun getNewsById(id: String): Flow<News?> = dao.getNewsById(id)
        .map { it?.toDomain() }

    override suspend fun refreshNews() {
        val remoteNews = api.getNews()
        dao.deleteAll()
        dao.insertAll(remoteNews.map { it.toEntity() })
    }
}