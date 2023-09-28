package dev.mkao.weaver.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mkao.weaver.domain.repository.Repository
import dev.mkao.weaver.ui.ArticleStates
import dev.mkao.weaver.ui.EventsHolder
import dev.mkao.weaver.util.Assets
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleScreenViewModel @Inject constructor(
	private val repository: Repository) : ViewModel() {
	
	var state by mutableStateOf(ArticleStates())
	
	fun onUserEvent(event: EventsHolder){
		when(event){
			is EventsHolder.OnCategoryClicked-> {
				state = state.copy(category = event.category)
				getNewsArticles(state.category)
			}
			is EventsHolder.OnCloseIconClicked-> {
			
			}
			is EventsHolder.OnSearchCategoryChanged -> {
			
			}
			is EventsHolder.OnSearchIconClicked -> {
			
			}
			is EventsHolder.OnArticleCardClicked -> {
				state = state.copy(isSelected = event.article)
			}
		}
	}
	fun getNewsArticles(category: String) {
		viewModelScope.launch {
			state = state.copy(isLoading = true)
			val result = repository.getTopHeadlines(category)
			when (result) {
				is Assets.Success -> {
					state = state.copy(
						article = result.data?: emptyList(),
						isLoading = false,
						error = null
					)
				}
				is Assets.Error -> {
				state = state.copy(
					error = result.message,
					isLoading = false,
					article = emptyList()
				)
				}
			}
		}
	}
}
