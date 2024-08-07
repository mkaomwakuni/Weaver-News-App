package dev.mkao.weaver.domain.repository

import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.Country
import dev.mkao.weaver.util.Assets

interface Repository {

	suspend fun getTopHeadlines(category: String,country: String): Assets<List<Article>>

	suspend fun searchRequest(query: String): Assets<List<Article>>

	suspend fun deleteArticle(article: Article)

	suspend fun getArticles():  Assets<List<Article>>

	suspend fun insertedArticle(article: Article)

	suspend fun getBookedArticles(): Assets<List<Article>>

	suspend fun getSelectedCountry(): Country?

	suspend fun setSelectedCountry(country: Country)
}