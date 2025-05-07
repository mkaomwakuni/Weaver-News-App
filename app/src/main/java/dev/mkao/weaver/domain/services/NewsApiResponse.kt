package dev.mkao.weaver.domain.services

import dev.mkao.weaver.domain.model.Article

data class NewsApiResponse(
    val articles: List<Article>,
    val status :String,
    val totalResults: Int
)