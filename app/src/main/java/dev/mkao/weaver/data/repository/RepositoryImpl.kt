package dev.mkao.weaver.data.repository

import dev.mkao.weaver.data.remote.NewsApi
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.repository.Repository
import dev.mkao.weaver.util.Assets


class RepositoryImpl(
	private val newsApi: NewsApi
): Repository {
	
	override suspend fun getTopHeadlines(category: String): Assets<List<Article>> {
		return try {
			val response = newsApi.getTopHeadlines(category = category)
			Assets.Success(data = response.articles)
		} catch (e: Exception) {
			Assets.Error(message = "Failed to fetch news ${e.message}")
		}
	}
	
	override suspend fun searchForNews(query: String): Assets<List<Article>> {
		return try {
			val response = newsApi.searchForNews(query = query)
			Assets.Success(data = response.articles)
		} catch (e: Exception) {
			Assets.Error(message = "Failed to fetch news ${e.message}")
		} 
	}
}
