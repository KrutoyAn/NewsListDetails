package ru.mygames.newslist.presentation.navigation

sealed class Screen(val route: String) {
    object NewsList : Screen("news_list")
    object NewsDetail : Screen("news_detail/{newsId}") {
        fun createRoute(newsId: String) = "news_detail/$newsId"
    }
}