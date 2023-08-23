package dev.mkao.weaver.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.repository.Repository
import dev.mkao.weaver.util.Assets
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleScreenViewModel @Inject constructor (
	private val repository: Repository) : ViewModel() {
	
	var newsArticles by mutableStateOf<List<Article>>(emptyList())
	
	private  fun getNewsArticle(category :String){
		viewModelScope.launch {
			val result = repository.getTopHeadlines(category = category)
			when (result){
				is  Assets.SuccessResponse -> {
					newsArticles = result.data ?: emptyList()
				}
				is Assets.ErrorResponse -> TODO()
			}
		}
	}
}