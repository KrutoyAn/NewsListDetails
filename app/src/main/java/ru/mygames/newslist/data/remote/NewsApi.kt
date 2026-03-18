package ru.mygames.newslist.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

data class NewsDto(
    val id: String,
    val title: String,
    val description: String,
    val content: String,
    val imageUrl: String,
    val date: String,
    val isNew: Boolean
)

interface NewsApi {
    @GET("news")
    suspend fun getNews(): List<NewsDto>

    @GET("news/{id}")
    suspend fun getNewsById(@Path("id") id: String): NewsDto
}