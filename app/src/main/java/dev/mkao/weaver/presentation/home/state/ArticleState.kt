package dev.mkao.weaver.presentation.home.state

import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.Edition

data class ArticleState(
    // Data states
    val articles: List<Article> = emptyList(),
    val sportsArticles: List<Article> = emptyList(),
    val entertainmentArticles: List<Article> = emptyList(),
    var isLoading: Boolean = false,
    val error: String? = null,
    val category: String = "General",

    // UI states
    val isResultsVisible: Boolean = false,
    val isSearchBarVisible: Boolean = false,
    val selectedArticle: Article? = null,
    val searchQuery: String = "",

    // Settings states
    val selectedCountry: Edition? = null,
    val selectedLanguage: String = "en"
)