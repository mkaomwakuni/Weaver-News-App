package dev.mkao.weaver.domain.repository

import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.util.Assets

interface Repository {

	suspend fun getTopHeadlines(category: String): Assets<List<Article>>
	suspend fun searchRequest(query: String): Assets<List<Article>>

	suspend fun upsertArticle(article: Article)

	suspend fun deleteArticle(article: Article)

	fun getArticles():  Assets<List<Article>>

	suspend fun getArticle(url: String): Article?
}