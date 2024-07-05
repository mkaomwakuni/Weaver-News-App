package dev.mkao.weaver.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.repository.Repository
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
    val selectedCategory: StateFlow<String?> = _selectedCategory

    private val _bookmarkedArticles = MutableStateFlow<List<Article>>(emptyList())
    val bookmarkedArticles: StateFlow<List<Article>> = _bookmarkedArticles.asStateFlow()

    init {
        fetchBookedArticles()
    }

    private fun fetchBookedArticles() {
        viewModelScope.launch {
            when (val result = repository.getBookedArticles()) {
                is Assets.Success -> _bookmarkedArticles.value = result.data!!
                is Assets.Error -> _bookmarkedArticles.value = emptyList()
            }
        }
    }

    fun selectArticle(article: Article) {
        viewModelScope.launch {
            _selectedArticle.value = article
        }
    }

    fun selectCategory(category: String) {
        viewModelScope.launch {
            _selectedCategory.value = category
        }
    }

    fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            val updatedArticle = article.copy(isBookedMarked = !article.isBookedMarked)
            if (updatedArticle.isBookedMarked) {
                repository.insertedArticle(article)
                Log.d("SharedViewModel", "Article bookmarked: ${updatedArticle.title}")
            } else {
                repository.deleteArticle(article)
                Log.d("SharedViewModel", "Article unbookmarked: ${updatedArticle.title}")
            }
            fetchBookedArticles()  // Refresh the list after toggling
        }
    }
    fun isArticleBookmarked(url: String): Flow<Boolean> = flow {
        when (val result = repository.getBookedArticles()) {
            is Assets.Success -> {
                result.data?.let { emit(it.any { it.url == url }) }
                Log.d("SharedViewModel", "Loaded ${result.data?.size} bookmarked articles")
            }
            is Assets.Error -> {
                emit(false)
                Log.e("SharedViewModel", "Error loading bookmarked articles: ${result.message}")
            }
        }
    }
}
