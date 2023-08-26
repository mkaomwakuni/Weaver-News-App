package dev.mkao.weaver.domain.model

data class NewsApiResponse(
	val articles: List<Article>,
	val Status :String,
	val totalResults: Int
)