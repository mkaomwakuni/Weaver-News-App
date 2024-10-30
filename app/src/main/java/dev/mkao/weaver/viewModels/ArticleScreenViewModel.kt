package dev.mkao.weaver.viewModels

import android.util.Log
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
	private val repository: Repository
) : ViewModel() {

	var state by mutableStateOf(ArticleStates())
	private var searchJob: Job? = null

	init {
		// Fetch initial articles for default categories
		fetchInitialArticles()
	}

	private fun fetchInitialArticles() {
		// Fetch articles for default categories
		getNewsArticlesCustom("Sports")
		getNewsArticlesCustom("Entertainment")
	}

	fun onUserEvent(event: EventsHolder) {
		when (event) {
			is EventsHolder.OnCategoryClicked -> {
				if (event.category in listOf("Sports", "Entertainment")) {
					getNewsArticlesCustom(event.category)
				} else {
					getNewsArticles(event.category)
				}
			}
			is EventsHolder.OnCloseIconClicked -> {
				state = state.copy(isSearchBarVisible = false)
				if (state.category in listOf("Sports", "Entertainment")) {
					getNewsArticlesCustom(state.category)
				} else {
					getNewsArticles(state.category)
				}
			}
			is EventsHolder.OnSearchCategoryChanged -> {
				state = state.copy(searchRequest = event.searchRequest)
				searchJob?.cancel()
				searchJob = viewModelScope.launch {
					delay(1000)
					searchForNews(query = state.searchRequest)
				}
			}
			is EventsHolder.OnSearchIconClicked -> {
				state = state.copy(isResultsVisible = true, article = emptyList())
			}
			is EventsHolder.OnArticleCardClicked -> {
				state = state.copy(isSelected = event.article)
			}
		}
	}

	fun updateCategoryAndFetchArticles(
		categoryApiValue: String,
		category: String,
		apiCountry: String = "us",
		lang: String = "en") {
		viewModelScope.launch {
			state = state.copy(isLoading = true)
			val result = repository.getTopHeadlines(
				category = categoryApiValue ,
				country = apiCountry,
				lang = lang
			)
			when (result) {
				is Assets.Success -> {
					state = state.copy(
						article = result.data ?: emptyList(),
						isLoading = false,
						error = null,
						category = categoryApiValue
					)
				}
				is Assets.Error -> {
					state = state.copy(
						isLoading = false,
						error = result.message,
						article = emptyList()
					)
				}
			}
		}
	}

	private fun getNewsArticlesCustom(category: String, apiCountry: String = "us", lang: String = "en") {
		viewModelScope.launch {
			val normalizedCategory = category.lowercase()
			state = state.copy(isLoading = true)
			val result = repository.getTopHeadlines(
				country = apiCountry,
				category = normalizedCategory,
				lang = lang
			)
			when (result) {
				is Assets.Success -> {
					val article = result.data?: emptyList()
					if (category == "Sports") {
						state = state.copy(
							sportsArticles = article,
							isLoading = false,
							error = null
						)
					}
					else if (category == "Entertainment") {
						state = state.copy(
							entertainmentArticles = article,
							isLoading = false,
							error = null
						)
					}
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

	private fun getNewsArticles(category: String, apiCountry: String = "us", lang: String = "en") {
		viewModelScope.launch {
			state = state.copy(isLoading = true)
			val result = repository.getTopHeadlines(
				country = apiCountry,
				category = category,
				lang = lang
			)
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

	private fun searchForNews(query: String) {
		if (query.isEmpty()) return

		viewModelScope.launch {
			state = state.copy(isLoading = true)
			when (val result = repository.searchRequest(query)) {
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
