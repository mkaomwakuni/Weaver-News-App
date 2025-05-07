package dev.mkao.weaver.presentation.details.state

import dev.mkao.weaver.domain.model.Article

sealed class DetailEvent {
    data class ArticleSelected(val article: Article) : DetailEvent()
    data class CategorySelected(val category: String) : DetailEvent()
    data class ShareArticle(val article: Article) : DetailEvent()
    data class ToggleBookmark(val article: Article) : DetailEvent()
}