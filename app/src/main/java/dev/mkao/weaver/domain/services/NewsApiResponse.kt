package dev.mkao.weaver.domain.services

import dev.mkao.weaver.domain.model.Article

data class NewsApiResponse(
    val articles: List<Article>,
    val Status :String,
    val totalResults: Int
)