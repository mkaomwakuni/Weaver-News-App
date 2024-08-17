package dev.mkao.weaver.data.repository

import android.util.Log
import dev.mkao.weaver.data.remote.CountryDao
import dev.mkao.weaver.data.remote.NewsApi
import dev.mkao.weaver.data.remote.NewsDao
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.Country
import dev.mkao.weaver.domain.repository.Repository
import dev.mkao.weaver.util.Assets

/**
 * Implementation of the Repository interface.
 * This class handles the business logic and data manipulation for the app.
 *
 * @param newsApi The API service to fetch news articles.
 * @param newsDao The DAO to interact with the local database for articles.
 * @param countryDao The DAO to interact with the local database for countries.
 */
class RepositoryImpl(
	private val newsApi: NewsApi,
	private val newsDao: NewsDao,
	private val countryDao: CountryDao
) : Repository {

	/**
	 * Fetches top headlines from the API and caches them in the local database.
	 * If the API call fails, it retrieves cached articles from the database.
	 *
	 * @param apiCountry The country code to fetch headlines for.
	 * @param category The category of news to fetch.
	 * @return A wrapped list of articles.
	 */
	override suspend fun getTopHeadlines(country: String,category: String,lang: String): Assets<List<Article>> {
		return try {
			val response = newsApi.getTopHeadlines(country = country,category = category,lang = lang)
			val articles = response.articles
			// Cache the articles
			articles.forEach { newsDao.upsert(it) }

			Assets.Success(articles)
		} catch (e: Exception) {
			// Retrieve articles from DB if API Error
			val articles = newsDao.getArticles()
			Assets.Success(articles)
		}
	}

	/**
	 * Performs a search request using the provided query and caches the results.
	 *
	 * @param query The search query string.
	 * @return A wrapped list of search result articles.
	 */
	override suspend fun searchRequest(query: String): Assets<List<Article>> {
		return try {
			val response = newsApi.searchRequest(query = query)
			val articles = response.articles
			// Cache the searched articles
			articles.forEach { newsDao.upsert(it) }

			Assets.Success(articles) // Removed filtering
		} catch (e: Exception) {
			// Handle errors and return as Assets.Error
			Assets.Error(message = "404! API Error")
		}
	}

	/**
	 * Marks an article as not bookmarked by updating its status in the database.
	 *
	 * @param article The article to update.
	 */
	override suspend fun deleteArticle(article: Article) {
		val unBookedMarked = article.copy(isBookedMarked = false)
		newsDao.upsert(unBookedMarked)

	}

	/**
	 * Inserts or updates an article in the local database, marking it as bookmarked.
	 *
	 * @param article The article to insert or update.
	 */
	override suspend fun insertedArticle(article: Article) {
		val bookedMarked = article.copy(isBookedMarked = true)
		newsDao.upsert(bookedMarked)

	}

	/**
	 * Retrieves all bookmarked articles from the local database.
	 *
	 * @return A wrapped list of bookmarked articles.
	 */
	override suspend fun getBookedArticles(): Assets<List<Article>> {
		return try {
			val articles = newsDao.getBookedArticles()
			Assets.Success(articles)
		} catch (e: Exception) {
			Assets.Error(message = "Error")
		}
	}

	/**
	 * Retrieves the selected country from the local database.
	 * If no country is selected, the default country is set and returned.
	 *
	 * @return The currently selected country or the default country.
	 */
	override suspend fun getSelectedCountry(): Country {
		return countryDao.getAnyCountry() ?: getDefaultCountry()
	}

	/**
	 * Sets the provided country as the selected one in the database.
	 * Clears any previous selection.
	 *
	 * @param country The country to set as selected.
	 */
	override suspend fun setSelectedCountry(country: Country) {
		countryDao.clearDefaultCountry()
		val updatedCountry = country.copy(isSelected = true)
		countryDao.insertCountry(updatedCountry)
	}

	/**
	 * Sets Brazil as the default country if no country is selected.
	 *
	 * @return The default country.
	 */
	private suspend fun getDefaultCountry(): Country {
		val defaultCountry = Country("BR", "Brazil", isSelected = true)
		countryDao.insertCountry(defaultCountry)
		Log.d("RepositoryImpl", "Default country set: ${defaultCountry.name}")
		return defaultCountry
	}
}