package ru.mygames.newslist.domain.model

import java.time.LocalDate

data class News (
    val id: String,
    val title: String,
    val description: String,
    val content: String,
    val imageUrl: String,
    val date: LocalDate,
    val isNew: Boolean = false
)

enum class NewsCategory {
    PROMOTIONS,
    NEWS
}