package dev.mkao.weaver.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.Country
import dev.mkao.weaver.domain.repository.Repository
import dev.mkao.weaver.presentation.country.getDefaultCountry
import dev.mkao.weaver.util.Assets
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _bookmarkedArticles = MutableStateFlow<List<Article>>(emptyList())
    val bookmarkedArticles: StateFlow<List<Article>> = _bookmarkedArticles.asStateFlow()

    private val _selectedCountry = MutableStateFlow<Country?>(null)
    val selectedCountry: StateFlow<Country?> = _selectedCountry.asStateFlow()


    fun setSelectedCountry(country: Country) {
        viewModelScope.launch {
            repository.setSelectedCountry(country)
            _selectedCountry.value = country
        }
    }

    fun saveSelectedCountryToDatabase(country: Country) {
        viewModelScope.launch {
            repository.setSelectedCountry(country)
            Log.d("SharedViewModel", "Selected country saved to database: ${country.name}")
        }
    }

    fun getSelectedCountryFromDatabase() {
        viewModelScope.launch {
            val country = repository.getSelectedCountry()
            _selectedCountry.value = country ?: getDefaultCountry()
            Log.d("SharedViewModel", "Selected country retrieved from database: ${_selectedCountry.value?.name}")
        }
    }


    init {
        viewModelScope.launch {
            _selectedCountry.value = repository.getSelectedCountry()
            fetchBookedArticles()
        }
    }

    private fun fetchBookedArticles() {
        viewModelScope.launch {
            when (val result = repository.getBookedArticles()) {
                is Assets.Success -> {
                    _bookmarkedArticles.value = result.data!!
                    Log.d("SharedViewModel", "Retrieved ${result.data.size} bookmarked articles")
                }
                is Assets.Error -> {
                    _bookmarkedArticles.value = emptyList()
                    Log.e("SharedViewModel", "Error retrieving bookmarked articles: ${result.message}")
                }
            }
        }
    }

    fun selectArticle(article: Article) {
        viewModelScope.launch {
            _selectedArticle.value = article
            Log.d("SharedViewModel", "Article selected: ${article.title}")
        }
    }

    fun selectCategory(category: String) {
        viewModelScope.launch {
            _selectedCategory.value = category
            Log.d("SharedViewModel", "Category selected: $category")
        }
    }

    fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            val updatedArticle = article.copy(isBookedMarked = !article.isBookedMarked)
            if (updatedArticle.isBookedMarked) {
                repository.insertedArticle(updatedArticle)
                Log.d("SharedViewModel", "Article bookmarked: ${updatedArticle.title}")
            } else {
                repository.deleteArticle(updatedArticle)
                Log.d("SharedViewModel", "Article unbookmarked: ${updatedArticle.title}")
            }
            fetchBookedArticles()
        }
    }

    fun isArticleBookmarked(url: String): Flow<Boolean> = flow {
        when (val result = repository.getBookedArticles()) {
            is Assets.Success -> {
                emit(result.data?.any { it.url == url } == true)
                Log.d("SharedViewModel", "Loaded ${result.data?.size} bookmarked articles")
            }
            is Assets.Error -> {
                emit(false)
                Log.e("SharedViewModel", "Error loading bookmarked articles: ${result.message}")
            }
        }
    }
}
