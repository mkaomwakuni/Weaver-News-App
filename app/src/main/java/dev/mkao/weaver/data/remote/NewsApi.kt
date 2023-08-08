package dev.mkao.weaver.data.remote

import dev.mkao.weaver.domain.model.NewsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.nio.channels.spi.AbstractSelectionKey

interface NewsApi {
	//base url for the news api
	//https://newsapi.org/v2/everything?q=tesla&from=2023-07-06&sortBy=publishedAt&apiKey=2d8537ffd9a94e55b63b570ff2674a3a
	
	@GET("everything")
	suspend fun getTopHeadlines(
		@Query("apikey") apiKey: String = API_KEY,
		@Query("category") category:  String,
		@Query ("country") country : String = "Ke"
	
	):NewsApiResponse
	
	companion object{
		const val API_KEY = "2d8537ffd9a94e55b63b570ff2674a3a"
		const val BASE_URL = "https://newsapi.org/v2/"
	}
}