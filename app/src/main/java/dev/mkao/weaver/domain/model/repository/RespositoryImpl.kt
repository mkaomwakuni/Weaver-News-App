package dev.mkao.weaver.domain.model.repository

import dev.mkao.weaver.data.remote.NewsApi
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.util.Assets

class RespositoryImpl  (private val newsApi: NewsApi) :Repository{
	
	override suspend fun getTopHeadlines(category: String): Assets<List<Article>> {
	
		return  try {
			val response = newsApi.getTopHeadlines(category = category)
				Assets.SuccessResponse(response.article)
			}catch (e:Exception){
				Assets.ErrorResponse(message = "Oops Unable Fetch! ${e.message}")
		}
	}
}