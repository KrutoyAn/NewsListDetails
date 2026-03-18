package ru.mygames.newslist.presentation.newsList

import ru.mygames.newslist.domain.model.News

data class NewsDetailUiState(
    val news: News? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)