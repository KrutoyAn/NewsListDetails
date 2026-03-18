package ru.mygames.newslist.presentation.newsList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ru.mygames.newslist.domain.model.News
import ru.mygames.newslist.presentation.viewModel.NewsListViewModel
import java.time.format.DateTimeFormatter

@Composable
fun NewsListScreen(
    onNewsClick: (String) -> Unit,
    viewModel: NewsListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val filteredNews = remember(uiState.news, uiState.selectedTab) {
        uiState.news.filter {
            if (uiState.selectedTab == NewsTab.PROMOTIONS) !it.isNew else it.isNew
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color.White)
        ) {

            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Информация",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 16.dp)
                )

                CustomTabRow(
                    selectedTab = uiState.selectedTab,
                    onTabSelected = viewModel::onTabSelected
                )

                Spacer(modifier = Modifier.height(16.dp))

                when {
                    uiState.isLoading -> LoadingIndicator()
                    uiState.error != null -> ErrorMessage(uiState.error!!)
                    else -> NewsList(news = filteredNews, onNewsClick = onNewsClick)
                }
            }
        }
    }
}

@Composable
private fun CustomTabRow(
    selectedTab: NewsTab,
    onTabSelected: (NewsTab) -> Unit
) {
    val tabs = listOf("Акции" to NewsTab.PROMOTIONS, "Новости" to NewsTab.NEWS)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF2F2F2)
    ) {
        TabRow(
            selectedTabIndex = tabs.indexOfFirst { it.second == selectedTab },
            containerColor = Color.Transparent,
            indicator = { Box {} },
            divider = {}
        ) {
            tabs.forEach { (title, tab) ->
                val selected = selectedTab == tab
                Tab(
                    selected = selected,
                    onClick = { onTabSelected(tab) },
                    modifier = Modifier.padding(4.dp).clip(RoundedCornerShape(8.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (selected) Color.White else Color.Transparent)
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            color = if (selected) Color.Black else Color.Gray,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsList(
    news: List<News>,
    onNewsClick: (String) -> Unit
) {
    if (news.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Пока здесь ничего нет", color = Color.Gray)
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(news, key = { it.id }) { item ->
                NewsCard(
                    news = item,
                    onClick = { onNewsClick(item.id) }
                )
            }
        }
    }
}

@Composable
private fun NewsCard(
    news: News,
    onClick: () -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFFFD700),
        tonalElevation = 2.dp,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = news.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = news.title,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = news.date.format(dateFormatter),
                    color = Color.Black.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBar() {
    val items = listOf(
        NavItem("Главная", Icons.Default.Home),
        NavItem("Информация", Icons.Default.Info, selected = true),
        NavItem("Магазины", Icons.Default.ShoppingCart),
        NavItem("Профиль", Icons.Default.Person)
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val activeColor = Color(0xFFFFD700)
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (item.selected) activeColor else Color.LightGray
                    )
                },
                label = {
                    Text(
                        item.label,
                        color = if (item.selected) Color.Black else Color.LightGray,
                        fontSize = 11.sp,
                        fontWeight = if (item.selected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selected = item.selected,
                onClick = { },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

private data class NavItem(
    val label: String,
    val icon: ImageVector,
    val selected: Boolean = false
)

@Composable
private fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color(0xFFFFD700))
    }
}

@Composable
private fun ErrorMessage(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, color = Color.Red, modifier = Modifier.padding(16.dp))
    }
}