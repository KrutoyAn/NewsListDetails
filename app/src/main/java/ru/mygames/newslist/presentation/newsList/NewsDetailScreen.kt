package ru.mygames.newslist.presentation.newsList


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ru.mygames.newslist.presentation.common.DateBadge
import ru.mygames.newslist.presentation.common.NewBadge
import ru.mygames.newslist.presentation.viewModel.NewsDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    newsId: String,
    onBackClick: () -> Unit,
    viewModel: NewsDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onBackClick() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color(0xFF2196F3)
                        )
                        Text(
                            text = "Назад",
                            color = Color(0xFF2196F3),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingIndicator()
            uiState.error != null -> ErrorMessage(uiState.error!!)
            uiState.news != null -> NewsDetailContent(
                uiState = uiState,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun NewsDetailContent(
    uiState: NewsDetailUiState,
    modifier: Modifier = Modifier
) {
    val news = uiState.news!!
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = news.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (news.isNew) {
                NewBadge()
            }
            DateBadge(date = news.date)
        }

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = news.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = news.content,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black.copy(alpha = 0.8f),
            lineHeight = 24.sp
        )
    }
}

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