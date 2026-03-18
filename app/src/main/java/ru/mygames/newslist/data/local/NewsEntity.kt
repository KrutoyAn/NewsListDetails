package ru.mygames.newslist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val content: String,
    val imageUrl: String,
    val date: String,
    val isNew: Boolean
)