package dev.mkao.weaver.data.repository

import dev.mkao.weaver.data.remote.NewsApi
import dev.mkao.weaver.data.remote.NewsDao
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.repository.Repository
import dev.mkao.weaver.util.Assets

class RepositoryImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao,
) : Repository {

    override suspend fun getTopHeadlines(
        country: String?,
        category: String,
        lang: String?
    ): Assets<List<Article>> {
        return try {
            val response = newsApi.getTopHeadlines(
                country = country ?: "us",
                category = category,
                lang = lang ?: "en"
            )
            val articles = response.articles
            // Cache the articles
            articles.forEach { newsDao.upsert(it) }

            Assets.Success(articles)
        } catch (e: Exception) {

            val articles = newsDao.getArticles()
            Assets.Success(articles)
        }
    }

    override suspend fun searchRequest(query: String): Assets<List<Article>> {
        return try {
            val response = newsApi.searchRequest(query = query)
            val articles = response.articles
            // Cache the searched articles
            articles.forEach { newsDao.upsert(it) }

            Assets.Success(articles)
        } catch (e: Exception) {
            // Handle errors and return as Assets.Error
            Assets.Error(message = "404! API Error")
        }
    }

    override suspend fun deleteArticle(article: Article) {
        val unBookedMarked = article.copy(isBookedMarked = false)
        newsDao.upsert(unBookedMarked)
    }

    override suspend fun insertedArticle(article: Article) {
        val bookedMarked = article.copy(isBookedMarked = true)
        newsDao.upsert(bookedMarked)
    }

    override suspend fun getBookedArticles(): Assets<List<Article>> {
        return try {
            val articles = newsDao.getBookedArticles()
            Assets.Success(articles)
        } catch (e: Exception) {
            Assets.Error(message = "Error")
        }
    }
}