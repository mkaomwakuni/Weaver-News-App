package dev.mkao.weaver.presentation.bookmarks


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.repository.Repository
import dev.mkao.weaver.presentation.bookmarks.state.BookmarkEvent
import dev.mkao.weaver.presentation.bookmarks.state.BookmarkState
import dev.mkao.weaver.util.Assets
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow(BookmarkState())
    val state: StateFlow<BookmarkState> = _state.asStateFlow()

    init {
        onEvent(BookmarkEvent.FetchBookmarks)
    }

    fun onEvent(event: BookmarkEvent) {
        when (event) {
            is BookmarkEvent.FetchBookmarks -> {
                fetchBookmarkedArticles()
            }

            is BookmarkEvent.ToggleBookmark -> {
                toggleBookmark(event.article)
            }

            is BookmarkEvent.ArticleClicked -> {
               
            }
        }
    }

  fun fetchBookmarkedArticles() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = repository.getBookedArticles()) {
                is Assets.Success -> {
                    _state.update {
                        it.copy(
                            bookmarkedArticles = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Assets.Error -> {
                    _state.update {
                        it.copy(
                            bookmarkedArticles = emptyList(),
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            val updatedArticle = article.copy(isBookedMarked = !article.isBookedMarked)

            if (updatedArticle.isBookedMarked) {
                repository.insertedArticle(updatedArticle)
            } else {
                repository.deleteArticle(updatedArticle)

                // Update local state immediately for responsive UI
                val currentList = _state.value.bookmarkedArticles.toMutableList()
                currentList.removeIf { it.url == updatedArticle.url }
                _state.update { it.copy(bookmarkedArticles = currentList) }
            }

            // Refresh to ensure DB and UI are in sync
            fetchBookmarkedArticles()
        }
    }

    fun isArticleBookmarked(url: String): Flow<Boolean> = flow {
        emit(state.value.bookmarkedArticles.any { it.url == url })
    }
}