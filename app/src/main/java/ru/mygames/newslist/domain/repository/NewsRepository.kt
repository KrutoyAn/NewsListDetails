package ru.mygames.newslist.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.mygames.newslist.domain.model.News

interface NewsRepository {
    fun getAllNews(): Flow<List<News>>
    fun getNewsById(id: String): Flow<News?>
    suspend fun refreshNews()
}