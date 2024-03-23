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
			val articles = response.articles?: emptyList()

			val filteredSnippet = articles.filterNot { article ->
				article.title.contains("removed",ignoreCase = true) ||
						article.content?.contains("removed", ignoreCase = true) == true
			}
			Assets.Success(filteredSnippet)
		} catch (e: Exception) {
			Assets.Error(message = "Failed to fetch news ${e.message}")
		}
	}

	override suspend fun searchRequest(query: String): Assets<List<Article>> {
		return try {
			// Use the newsApi to fetch search results using the provided query
			val response = newsApi.searchRequest(query = query)

			// Return the search results as Success
			Assets.Success(data = response.articles)
		} catch (e: Exception) {
			// Handle errors and return as Assets.Error
			Assets.Error(message = "404! Api Error ")
		}
	}
}
