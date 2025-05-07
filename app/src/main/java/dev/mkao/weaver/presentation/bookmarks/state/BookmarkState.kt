package dev.mkao.weaver.presentation.bookmarks.state

import dev.mkao.weaver.domain.model.Article

data class BookmarkState(
    val bookmarkedArticles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)