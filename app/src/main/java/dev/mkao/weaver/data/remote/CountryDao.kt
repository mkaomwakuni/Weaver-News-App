package dev.mkao.weaver.data.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.mkao.weaver.domain.model.Country
@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country)

    @Query("SELECT * FROM Country WHERE isSelected = 1 LIMIT 1")
    suspend fun getAnyCountry(): Country?

    @Query("UPDATE Country SET isSelected = 0 WHERE isSelected = 1")
    suspend fun clearDefaultCountry()
}