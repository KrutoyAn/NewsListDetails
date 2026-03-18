package ru.mygames.newslist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY date DESC")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE id = :id")
    fun getNewsById(id: String): Flow<NewsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Query("DELETE FROM news")
    suspend fun deleteAll()
}