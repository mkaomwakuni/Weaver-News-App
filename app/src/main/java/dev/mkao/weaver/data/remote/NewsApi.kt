package dev.mkao.weaver.data.remote

import dev.mkao.weaver.domain.model.NewsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
	//base url for the news api
	//https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=""
	@GET("top-headlines")
	suspend fun getTopHeadlines(
		@Query("category") category:  String,
		@Query ("country") country : String,
		@Query("apikey") apiKey: String = API_KEY,
	
	): NewsApiResponse

	@GET("everything")
	suspend fun searchRequest(
		@Query("q") query: String,
		@Query("apiKey") apiKey: String = API_KEY
	): NewsApiResponse

	companion object{
		const val API_KEY = "YOUR API KEY HERE"
		const val BASE_URL = "https://newsapi.org/v2/"
	}
}