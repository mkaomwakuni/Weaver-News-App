package dev.mkao.weaver.data.repository

import dev.mkao.weaver.data.remote.NewsApi
import dev.mkao.weaver.data.remote.NewsDao
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.repository.Repository
import dev.mkao.weaver.util.Assets


class RepositoryImpl(
	private val newsApi: NewsApi,
	private val newsDao: NewsDao
): Repository {
	
	override suspend fun getTopHeadlines(category: String): Assets<List<Article>> {
		return try {
			val response = newsApi.getTopHeadlines(category = category)
			val articles = response.articles

			Assets.Success(filterRemovedArticles(articles))
		} catch (e: Exception) {
			Assets.Error(message = "404! Api Error")
		}
	}

	override suspend fun searchRequest(query: String): Assets<List<Article>> {
		return try {
			// Use the newsApi to fetch search results using the provided query
			val response = newsApi.searchRequest(query = query)
			val articles = response.articles

			// Return the search results as Success after filtering
			Assets.Success(filterRemovedArticles(articles))
		} catch (e: Exception) {
			// Handle errors and return as Assets.Error
			Assets.Error(message = "404! Api Error")
		}
	}

	override suspend fun upsertArticle(article: Article) {
		newsDao.upsert(article)
	}

	override suspend fun deleteArticle(article: Article) {
		newsDao.delete(article)
	}


	override suspend fun getArticle(url: String): Article? {
		TODO("Not yet implemented")
	}
	override fun getArticles(): Assets<List<Article>> {
		return try {
			val articles = newsDao.getArticles()
			Assets.Success(articles)
		} catch (e: Exception) {
			Assets.Error(message = "Error")
		}
	}

	private fun filterRemovedArticles(articles: List<Article>): List<Article> {
		return articles.filterNot { article ->
			article.title.contains("removed", ignoreCase = true) ||
					article.content?.contains("removed", ignoreCase = true) == true
		}
	}

}
