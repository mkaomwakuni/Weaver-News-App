package dev.mkao.weaver.presentation.home.state

import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.Edition

sealed class ArticleEvent {
    data class CategorySelected(val category: String) : ArticleEvent()
    object RefreshArticles : ArticleEvent()
    data class SearchQueryChanged(val query: String) : ArticleEvent()
    object SearchClicked : ArticleEvent()
    object CloseSearch : ArticleEvent()
    data class ArticleSelected(val article: Article) : ArticleEvent()
    data class CountryLanguageChanged(val country: Edition, val languageCode: String) :
        ArticleEvent()

    data class ToggleBookmark(val article: Article) : ArticleEvent()
}