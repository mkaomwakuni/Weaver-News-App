package dev.mkao.weaver.domain.model

data class NewsApiResponse(
	val article: List<Article>,
	val ApiStatus :String,
	val ReturnedResult: Int
)