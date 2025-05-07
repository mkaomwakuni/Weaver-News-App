package dev.mkao.weaver.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class AppPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LANGUAGE_CODE = "language_code"
        private const val KEY_COUNTRY_CODE = "country_code"
        private const val DEFAULT_LANGUAGE = "en"
        private const val DEFAULT_COUNTRY = "us"
    }

    // Get selected language
    fun getLanguageCode(): String {
        val langCode =
            sharedPreferences.getString(KEY_LANGUAGE_CODE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
        return langCode
    }

    // Get  selected country
    fun getCountryCode(): String {
        val countryCode =
            sharedPreferences.getString(KEY_COUNTRY_CODE, DEFAULT_COUNTRY) ?: DEFAULT_COUNTRY
        return countryCode
    }

    // Save language preference
    fun saveLanguageCode(languageCode: String) {
        sharedPreferences.edit { putString(KEY_LANGUAGE_CODE, languageCode) }
    }

    // Save  country preference
    fun saveCountryCode(countryCode: String) {
        sharedPreferences.edit { putString(KEY_COUNTRY_CODE, countryCode) }
    }
}