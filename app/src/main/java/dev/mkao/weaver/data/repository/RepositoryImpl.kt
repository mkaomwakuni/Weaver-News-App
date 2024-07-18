package dev.mkao.weaver.data.repository

import android.util.Log
import dev.mkao.weaver.data.remote.CountryDao
import dev.mkao.weaver.data.remote.NewsApi
import dev.mkao.weaver.data.remote.NewsDao
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.Country
import dev.mkao.weaver.domain.repository.Repository
import dev.mkao.weaver.util.Assets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RepositoryImpl(
	private val newsApi: NewsApi,
	private val newsDao: NewsDao,
	private  val countryDao: CountryDao
): Repository {

	override suspend fun getTopHeadlines(category: String,selectedCountry: String): Assets<List<Article>> {
		return try {
			val response = newsApi.getTopHeadlines(category = category,selectedCountry)
			val articles = response.articles
			//cache the articles
			articles.forEach { newsDao.upsert(it) }

			Assets.Success(filterRemovedArticles(articles))
		} catch (e: Exception) {
			//Retrieve articles from DB if API Error
			val articles = newsDao.getArticles()
			Assets.Success(filterRemovedArticles(articles))
		}
	}

	override suspend fun searchRequest(query: String): Assets<List<Article>> {
		return try {
			// Use the newsApi to fetch search results using the provided query
			val response = newsApi.searchRequest(query = query)
			val articles = response.articles
			//Cache the searched articles
			articles.forEach { newsDao.upsert(it) }

			// Return the search results as Success after filtering
			Assets.Success(filterRemovedArticles(articles))
		} catch (e: Exception) {
			// Handle errors and return as Assets.Error
			Assets.Error(message = "404! Api Error")
		}
	}


	override suspend fun deleteArticle(article: Article) {
		val unBookedMarked = article.copy(isBookedMarked = false)
		newsDao.upsert(unBookedMarked)  // Update instead of delete
		Log.d("RepositoryImpl", "Article deleted: ${article.title}")
	}


	override suspend fun getArticles(): Assets<List<Article>> {
		return try {
			withContext(Dispatchers.IO) {
				val articles = newsDao.getArticles()
				Assets.Success(articles)
			}
		} catch (e: Exception) {
			Assets.Error(message = "Error")
		}
	}

	override suspend fun insertedArticle(article: Article) {
		val bookedMarked = article.copy(isBookedMarked = true)
		newsDao.upsert(bookedMarked)
		Log.d("RepositoryImpl", "Article inserted/updated: ${article.title}")
	}


	override suspend fun getBookedArticles(): Assets<List<Article>> {
		return try {
			val articles = newsDao.getBookedArticles()
			Log.d("RepositoryImpl", "Retrieved ${articles.size} bookmarked articles")
			Assets.Success(articles)
		} catch (e:Exception){
			Log.e("RepositoryImpl", "Error retrieving bookmarked articles", e)
			Assets.Error(message = "Error")
		}
	}

	private fun filterRemovedArticles(articles: List<Article>): List<Article> {
		return articles.filterNot { article ->
			article.title.contains("removed", ignoreCase = true) ||
					article.content?.contains("removed", ignoreCase = true) == true
		}
	}
	override suspend fun getSelectedCountry(): Country? {
		return countryDao.getAnyCountry() ?: getDefaultCountry()
	}

	override suspend fun setSelectedCountry(country: Country) {
		countryDao.clearDefaultCountry()
		val updatedCountry = country.copy(isSelected = true)
		countryDao.insertCountry(updatedCountry)
		Log.d("RepositoryImpl", "Selected country set: ${updatedCountry.name}")
	}

	private suspend fun getDefaultCountry(): Country {
		val defaultCountry = Country("BR", "Brazil", isSelected = true)
		countryDao.insertCountry(defaultCountry)
		Log.d("RepositoryImpl", "Default country set: ${defaultCountry.name}")
		return defaultCountry
	}

}
