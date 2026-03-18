package ru.mygames.newslist.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mygames.newslist.domain.model.News
import ru.mygames.newslist.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsListUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<List<News>> = repository.getAllNews()
}