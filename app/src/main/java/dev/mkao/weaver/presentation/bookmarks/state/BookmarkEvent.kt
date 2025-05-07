package dev.mkao.weaver.presentation.bookmarks.state

import dev.mkao.weaver.domain.model.Article

sealed class BookmarkEvent {
    object FetchBookmarks : BookmarkEvent()
    data class ToggleBookmark(val article: Article) : BookmarkEvent()
    data class ArticleClicked(val article: Article) : BookmarkEvent()
}