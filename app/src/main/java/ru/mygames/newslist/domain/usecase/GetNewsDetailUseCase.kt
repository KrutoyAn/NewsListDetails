package ru.mygames.newslist.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mygames.newslist.domain.model.News
import ru.mygames.newslist.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsDetailUseCase @Inject constructor(
    private val repository: NewsRepository)
{
    operator fun invoke(id: String): Flow<News?> = repository.getNewsById(id)
}