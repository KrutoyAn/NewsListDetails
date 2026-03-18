package ru.mygames.newslist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.mygames.newslist.presentation.newsList.NewsDetailScreen
import ru.mygames.newslist.presentation.newsList.NewsListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.NewsList.route
    ) {
        composable(Screen.NewsList.route) {
            NewsListScreen(
                onNewsClick = { newsId ->
                    navController.navigate(Screen.NewsDetail.createRoute(newsId))
                }
            )
        }

        composable(Screen.NewsDetail.route) { backStackEntry ->
            val newsId = backStackEntry.arguments?.getString("newsId") ?: return@composable
            NewsDetailScreen(
                newsId = newsId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}