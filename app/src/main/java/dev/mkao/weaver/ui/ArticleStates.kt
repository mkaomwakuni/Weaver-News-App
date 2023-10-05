package dev.mkao.weaver.ui

import dev.mkao.weaver.domain.model.Article

data class ArticleStates(
	val error: String? = null,
	val isResultsVisible: Boolean = false,
	val isSearchBarVisible: Boolean = false,
	val isLoading: Boolean = false,
	val category: String = "General",
	val isSelected: Article? = null,
	val SearchRequest: String = "",
	val article: List<Article> = emptyList()
)
