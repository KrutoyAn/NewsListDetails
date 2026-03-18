package ru.mygames.newslist.presentation.newsList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            Text(
                text = "Информация",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            CustomTabRow(
                selectedTab = uiState.selectedTab,
                onTabSelected = viewModel::onTabSelected
            )

            Spacer(modifier = Modifier.height(8.dp))

            when {
                uiState.isLoading -> LoadingIndicator()
                uiState.error != null -> ErrorMessage(uiState.error!!)
                else -> NewsList(
                    news = uiState.news,
                    onNewsClick = onNewsClick
                )
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
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF5F5F5)
    ) {
        TabRow(
            selectedTabIndex = tabs.indexOfFirst { it.second == selectedTab },
            containerColor = Color.Transparent,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabs.indexOfFirst { it.second == selectedTab }]),
                    color = Color.Transparent
                )
            },
            divider = {}
        ) {
            tabs.forEach { (title, tab) ->
                val selected = selectedTab == tab
                Tab(
                    selected = selected,
                    onClick = { onTabSelected(tab) },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (selected) Color.White else Color.Transparent)
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            color = if (selected) Color.Black else Color.Gray,
                            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
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
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
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
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFFFD700)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = news.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = news.title,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = news.date.format(dateFormatter),
                    color = Color.Black.copy(alpha = 0.7f),
                    fontSize = 13.sp
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
        containerColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (item.selected) Color(0xFFFFD700) else Color.Gray
                    )
                },
                label = {
                    Text(
                        item.label,
                        color = if (item.selected) Color(0xFFFFD700) else Color.Gray,
                        fontSize = 12.sp
                    )
                },
                selected = item.selected,
                onClick = { }
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color(0xFFFFD700))
    }
}

@Composable
private fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    }
}