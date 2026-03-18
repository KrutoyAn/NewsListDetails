package ru.mygames.newslist.data.mapper

import ru.mygames.newslist.data.local.NewsEntity
import ru.mygames.newslist.data.remote.NewsDto
import ru.mygames.newslist.domain.model.News
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object NewsMapper {
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun NewsDto.toDomain(): News = News(
        id = id,
        title = title,
        description = description,
        content = content,
        imageUrl = imageUrl,
        date = LocalDate.parse(date, formatter),
        isNew = isNew
    )

    fun NewsEntity.toDomain(): News = News(
        id = id,
        title = title,
        description = description,
        content = content,
        imageUrl = imageUrl,
        date = LocalDate.parse(date, formatter),
        isNew = isNew
    )

    fun News.toEntity(): NewsEntity = NewsEntity(
        id = id,
        title = title,
        description = description,
        content = content,
        imageUrl = imageUrl,
        date = date.format(formatter),
        isNew = isNew
    )

    fun NewsDto.toEntity(): NewsEntity = NewsEntity(
        id = id,
        title = title,
        description = description,
        content = content,
        imageUrl = imageUrl,
        date = date,
        isNew = isNew
    )
}