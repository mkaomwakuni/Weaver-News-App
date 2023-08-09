package dev.mkao.weaver.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.repository.Repository
import dev.mkao.weaver.util.Assets
import kotlinx.coroutines.launch

class NewsViewModel (private val repository: Repository) :ViewModel() {
	
	var newsArticle by mutableStateOf<List<Article>>(emptyList())
	
	private  fun getNewsArticle(category :String){
		viewModelScope.launch {
			val result = repository.getTopHeadlines(category = category)
			when (result){
				is  Assets.SuccessResponse -> {
					newsArticle = result.data ?: emptyList()
				}
				is Assets.ErrorResponse -> TODO()
			}
		}
	}
}