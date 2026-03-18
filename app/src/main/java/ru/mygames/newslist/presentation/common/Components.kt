package ru.mygames.newslist.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun NewBadge(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFFFFD700))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "Новость",
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun DateBadge(
    date: LocalDate,
    modifier: Modifier = Modifier
) {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFFFFD700))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = date.format(formatter),
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}