package dev.mkao.weaver.data.remote

import dev.mkao.weaver.domain.services.NewsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

	@GET("top-headlines")
	suspend fun getTopHeadlines(
		@Query("country") country: String,
		@Query("category") category: String = "General",
		@Query("lang") lang: String,
		@Query("max") max: Int = 10,
		@Query("apikey") apiKey: String = API_KEY
	): NewsApiResponse

	@GET("search")
	suspend fun searchRequest(
		@Query("q") query: String,
		@Query("lang") lang: String,
		@Query("country") country: String? = null,
		@Query("max") max: Int = 6,
		@Query("apikey") apiKey: String = API_KEY
	): NewsApiResponse

	companion object {
		const val API_KEY = "YOUR API KEY"
		const val BASE_URL = "https://gnews.io/api/v4/"
	}
}
