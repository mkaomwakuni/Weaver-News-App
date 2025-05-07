package dev.mkao.weaver.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.repository.Repository
import dev.mkao.weaver.presentation.details.state.DetailEvent
import dev.mkao.weaver.presentation.details.state.DetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.ArticleSelected -> {
                viewModelScope.launch {
                    _state.update { it.copy(selectedArticle = event.article) }
                }
            }

            is DetailEvent.CategorySelected -> {
                viewModelScope.launch {
                    _state.update { it.copy(selectedCategory = event.category) }
                }
            }

            is DetailEvent.ToggleBookmark -> {
                toggleBookmark(event.article)
            }

            is DetailEvent.ShareArticle -> {

            }
        }
    }

    private fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            val updatedArticle = article.copy(isBookedMarked = !article.isBookedMarked)
            if (updatedArticle.isBookedMarked) {
                repository.insertedArticle(updatedArticle)
            } else {
                repository.deleteArticle(updatedArticle)
            }

            _state.value.selectedArticle?.let { selectedArticle ->
                if (selectedArticle.url == article.url) {
                    _state.update { it.copy(selectedArticle = updatedArticle) }
                }
            }
        }
    }
}