package dev.mkao.weaver.data.remote

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.mkao.weaver.domain.model.Article

@Database(entities = [Article::class], version = 2, exportSchema = false)
@TypeConverters(NewsTypeConvertor::class)
abstract class NewsDatabase : RoomDatabase() {
    /**
     * Provides access to the DAO (Data Access Object) for the `Article` entity.
     *
     * @return An instance of `NewsDao`, which contains methods to interact with the `Article` table.
     */
    abstract fun articleDao(): NewsDao
}
