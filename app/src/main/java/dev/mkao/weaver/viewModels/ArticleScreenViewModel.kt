package dev.mkao.weaver.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mkao.weaver.domain.model.EventsHolder
import dev.mkao.weaver.domain.repository.Repository
import dev.mkao.weaver.util.Assets
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleScreenViewModel @Inject constructor(
	private val repository: Repository) : ViewModel() {

	var state by mutableStateOf(ArticleStates())
	private var searchJob: Job? = null
		private set

	init {
		// Fetch initial articles for Sports and Entertainment categories
		fetchInitialArticles()
	}

	private fun fetchInitialArticles() {
		getNewsArticlesCustom("Sports")
		getNewsArticlesCustom("Entertainment")
	}

	fun onUserEvent(event: EventsHolder){
		when(event){
			is EventsHolder.OnCategoryClicked-> {
				state = state.copy(category = event.category)
				getNewsArticles(state.category)
			}
			is EventsHolder.OnCloseIconClicked-> {
				state = state.copy(isSearchBarVisible = false)
				getNewsArticles(category = state.category)
			}
			is EventsHolder.OnSearchCategoryChanged -> {
				state = state.copy(searchRequest = event.searchRequest)
				searchForNews(query = state.searchRequest)
				searchJob?.cancel()
				searchJob = viewModelScope.launch {
					delay(1000)
					searchForNews(query = state.searchRequest)
				}

			}
			is EventsHolder.OnSearchIconClicked -> {
				state = state.copy(
			     isResultsVisible = true,
				 article = emptyList()
				)
			}
			is EventsHolder.OnArticleCardClicked -> {
				state = state.copy(isSelected = event.article)
			}
		}
	}
	private fun getNewsArticles(category: String) {
		viewModelScope.launch {
			state = state.copy(isLoading = true)
			val selectedCountry = repository.getSelectedCountry()?.code ?: "us"
			val result = repository.getTopHeadlines(category = category,selectedCountry)
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
	private fun getNewsArticlesCustom(category: String) {
		viewModelScope.launch {
			state = state.copy(isLoading = true)
			val selectedCountry = repository.getSelectedCountry()?.code ?: "us"
			when (val result = repository.getTopHeadlines(category = category, selectedCountry)) {
				is Assets.Success -> {
					val articles = result.data ?: emptyList()
					if (category == "Sports") {
						state = state.copy(
							sportsArticles = articles,
							isLoading = false,
							error = null
						)
					} else if (category == "Entertainment") {
						state = state.copy(
							entertainmentArticles = articles,
							isLoading = false,
							error = null
						)
					}
				}
				is Assets.Error -> {
					state = state.copy(
						error = result.message,
						isLoading = false
					)
				}
			}
		}
	}
	private fun searchForNews(query: String) {
		if (query.isEmpty()){
			return
		}
		else {
			viewModelScope.launch {
				state = state.copy(isLoading = true)
				val result = repository.searchRequest(query = query)
				when (result) {
					is Assets.Success -> {
						state = state.copy(
							article = result.data ?: emptyList(),
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
}
