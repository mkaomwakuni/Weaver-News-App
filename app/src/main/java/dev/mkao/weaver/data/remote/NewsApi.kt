package dev.mkao.weaver.data.remote

import dev.mkao.weaver.domain.model.NewsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
	//base url for the news api
	//https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=2d8537ffd9a94e55b63b570ff2674a3a
	@GET("top-headlines")
	suspend fun getTopHeadlines(
		@Query("category") category:  String,
		@Query ("country") country : String = "us",
		@Query("apikey") apiKey: String = API_KEY,
	
	): NewsApiResponse
	@GET("everything")
	suspend fun searchRequest(
		@Query("q") query: String,
		@Query("apiKey") apiKey: String = API_KEY
	): NewsApiResponse
	companion object{
		const val API_KEY = "f36d74acc142444f96809b7b9395716b"
		const val BASE_URL = "https://newsapi.org/v2/"
	}
}