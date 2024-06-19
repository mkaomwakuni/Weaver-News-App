package dev.mkao.weaver.data.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.mkao.weaver.domain.model.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(NewsTypeConvertor::class)
abstract class NewsDatabase: RoomDatabase(){
    abstract fun articleDao():  NewsDao

    companion object {
        @Volatile private var instance: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                  NewsDatabase::class.java,
                    "articles"
                ).build().also { instance = it }
            }
    }
}