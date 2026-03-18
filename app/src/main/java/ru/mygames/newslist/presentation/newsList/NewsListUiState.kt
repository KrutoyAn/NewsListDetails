package ru.mygames.newslist.presentation.newsList

import ru.mygames.newslist.domain.model.News

data class NewsListUiState(
    val news: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedTab: NewsTab = NewsTab.PROMOTIONS
)

enum class NewsTab {
    PROMOTIONS,
    NEWS
}