package dev.mkao.weaver.data.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.mkao.weaver.domain.model.Country
@Dao
interface CountryDao {

    /**
     * Inserts a country into the database.
     * If the country already exists, it will be replaced.
     *
     * @param country The country object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country)

    /**
     * Retrieves the currently selected country from the database.
     *
     * @return The country object that is currently selected (where `isSelected` is 1),
     * or `null` if no country is selected.
     */
    @Query("SELECT * FROM Country WHERE isSelected = 1 LIMIT 1")
    suspend fun getAnyCountry(): Country?

    /**
     * Clears the default country by setting `isSelected` to 0
     * for any country that is currently selected.
     */
    @Query("UPDATE Country SET isSelected = 0 WHERE isSelected = 1")
    suspend fun clearDefaultCountry()
}
