package dev.mkao.weaver.data.remote

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.mkao.weaver.domain.model.Article

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Query("SELECT * FROM Articles")
    fun getArticles(): List<Article>

    @Delete
    suspend fun delete(article: Article)

}