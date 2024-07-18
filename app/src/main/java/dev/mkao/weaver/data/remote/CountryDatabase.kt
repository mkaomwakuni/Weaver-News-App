package dev.mkao.weaver.data.remote

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.mkao.weaver.domain.model.Country

@Database(entities = [ Country::class], version = 1, exportSchema = false)
@TypeConverters(NewsTypeConvertor::class)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun newsDao(): CountryDao
}