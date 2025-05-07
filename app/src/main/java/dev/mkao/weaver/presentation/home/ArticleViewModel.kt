package dev.mkao.weaver.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mkao.weaver.data.preferences.AppPreferencesManager
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.Edition
import dev.mkao.weaver.domain.repository.Repository
import dev.mkao.weaver.presentation.home.state.ArticleEvent
import dev.mkao.weaver.presentation.home.state.ArticleState
import dev.mkao.weaver.presentation.languages.LanguageConstants
import dev.mkao.weaver.util.Assets
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repository: Repository,
    private val preferencesManager: AppPreferencesManager
) : ViewModel() {

    private val _state = MutableStateFlow(ArticleState())
    val state: StateFlow<ArticleState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        // Load saved preferences
        val savedLanguage = preferencesManager.getLanguageCode()
        val countryCode = preferencesManager.getCountryCode()

        val languageEdition =
            LanguageConstants.languages.firstOrNull { it.code == savedLanguage }

        if (languageEdition != null) {

            val selectedCountry =
                if (countryCode.uppercase() in languageEdition.abbreviations) {
                    Edition(
                        languageEdition.code,
                        languageEdition.name,
                        LanguageConstants.countryMap[countryCode.uppercase()]
                            ?: languageEdition.name,
                        listOf(countryCode.uppercase())
                    )
                } else {

                    val defaultCountry = languageEdition.abbreviations.firstOrNull() ?: "US"
                    preferencesManager.saveCountryCode(defaultCountry.lowercase())

                    Edition(
                        languageEdition.code,
                        languageEdition.name,
                        LanguageConstants.countryMap[defaultCountry] ?: languageEdition.name,
                        listOf(defaultCountry)
                    )
                }

            _state.update {
                it.copy(
                    selectedCountry = selectedCountry,
                    selectedLanguage = savedLanguage
                )
            }
        } else {
            // Fallback to English/US if language not found
            val defaultLanguage = LanguageConstants.languages.find { it.code == "en" }
                ?: LanguageConstants.languages.first()

            val defaultCountry = Edition(
                defaultLanguage.code,
                defaultLanguage.name,
                "United States",
                listOf("US")
            )

            preferencesManager.saveLanguageCode(defaultLanguage.code)
            preferencesManager.saveCountryCode("us")

            _state.update {
                it.copy(
                    selectedCountry = defaultCountry,
                    selectedLanguage = defaultLanguage.code
                )
            }
        }

        fetchInitialArticles()
    }

    fun onEvent(event: ArticleEvent) {
        when (event) {
            is ArticleEvent.CategorySelected -> {
                if (event.category in listOf("Sports", "Entertainment")) {
                    getNewsArticlesCustom(
                        event.category.lowercase(),
                        state.value.selectedCountry?.code?.lowercase() ?: "us",
                        state.value.selectedLanguage
                    )
                } else {
                    getNewsArticles(
                        event.category.lowercase(),
                        state.value.selectedCountry?.code?.lowercase() ?: "us",
                        state.value.selectedLanguage
                    )
                }
            }

            is ArticleEvent.RefreshArticles -> {
                forceRefresh()
            }

            is ArticleEvent.SearchQueryChanged -> {
                _state.update { it.copy(searchQuery = event.query) }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000)
                    searchForNews(query = state.value.searchQuery)
                }
            }

            is ArticleEvent.SearchClicked -> {
                _state.update { it.copy(isResultsVisible = true, articles = emptyList()) }
            }

            is ArticleEvent.CloseSearch -> {
                _state.update { it.copy(isSearchBarVisible = false) }
                val category = state.value.category
                if (category.lowercase() in listOf("sports", "entertainment")) {
                    getNewsArticlesCustom(
                        category,
                        state.value.selectedCountry?.code?.lowercase() ?: "us",
                        state.value.selectedLanguage
                    )
                } else {
                    getNewsArticles(
                        category,
                        state.value.selectedCountry?.code?.lowercase() ?: "us",
                        state.value.selectedLanguage
                    )
                }
            }

            is ArticleEvent.ArticleSelected -> {
                _state.update { it.copy(
                    selectedArticle = event.article
                )
                }
            }

            is ArticleEvent.CountryLanguageChanged -> {
                // Immediately update the country in the UI state
                _state.update {
                    it.copy(selectedCountry = event.country, selectedLanguage = event.languageCode)
                }

                // Then update preferences and fetch articles
                updateSelectedCountry(
                    event.country,
                    event.languageCode
                )
            }

            is ArticleEvent.ToggleBookmark -> {
                toggleBookmark(
                    event.article
                )
            }
        }
    }

    private fun fetchInitialArticles() {
        getNewsArticlesCustom(
            "Sports",
            state.value.selectedCountry?.code ?: "us",
            state.value.selectedLanguage
        )
        getNewsArticlesCustom(
            "Entertainment",
            state.value.selectedCountry?.code ?: "us",
            state.value.selectedLanguage
        )
    }

    private fun updateSelectedCountry(country: Edition, languageCode: String) {
        // Save the preferences
        preferencesManager.saveLanguageCode(languageCode)
        preferencesManager.saveCountryCode(country.code.lowercase())

        _state.update { it.copy(articles = emptyList()) }
        fetchInitialArticles()

        val currentCategory = state.value.category
        if (currentCategory.isNotEmpty()) {
            if (currentCategory.lowercase() in listOf("sports", "entertainment")) {
                getNewsArticlesCustom(
                    currentCategory,
                    country.code,
                    languageCode
                )
            } else {
                getNewsArticles(
                    currentCategory,
                    country.code,
                    languageCode
                )
            }
        }
    }

    fun forceRefresh() {
        _state.update { 
            it.copy(
                isLoading = true,
                articles = emptyList(),
                sportsArticles = emptyList(),
                entertainmentArticles = emptyList()
            )
        }
        
        fetchInitialArticles()

        val currentCategory = state.value.category
        if (currentCategory.isNotEmpty()) {
            if (currentCategory.lowercase() in listOf("sports", "entertainment")) {
                getNewsArticlesCustom(
                    currentCategory,
                    state.value.selectedCountry?.code ?: "us",
                    state.value.selectedLanguage
                )
            } else {
                getNewsArticles(
                    currentCategory,
                    state.value.selectedCountry?.code ?: "us",
                    state.value.selectedLanguage
                )
            }
        }
    }

    private fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            val updatedArticle = article.copy(isBookedMarked = !article.isBookedMarked)

            if (updatedArticle.isBookedMarked) {
                repository.insertedArticle(updatedArticle)
            } else {
                repository.deleteArticle(updatedArticle)
            }

            state.value.selectedArticle?.let { selected ->
                if (selected.url == article.url) {
                    _state.update { it.copy(selectedArticle = updatedArticle) }
                }
            }
        }
    }

    fun updateCategoryAndFetchArticles(
        categoryApiValue: String,
        lang: String
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, category = categoryApiValue) }
            val result = repository.getTopHeadlines(
                category = categoryApiValue,
                country = _state.value.selectedCountry?.code?.lowercase() ?: "us",
                lang = lang
            )
            when (result) {
                is Assets.Success -> {
                    _state.update { currentState ->
                        currentState.copy(
                            articles = result.data ?: emptyList(),
                            isLoading = false,
                            error = null,
                            category = categoryApiValue
                        )
                    }
                }

                is Assets.Error -> {
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = result.message,
                            articles = emptyList()
                        )
                    }
                }
            }
        }
    }

    private fun getNewsArticlesCustom(
        category: String,
        countryCode: String,
        lang: String = _state.value.selectedLanguage
    ) {
        viewModelScope.launch {
            val normalizedCategory = category.lowercase()

            _state.update { it.copy(isLoading = true) }
            val result = repository.getTopHeadlines(
                country = countryCode,
                category = normalizedCategory,
                lang = lang
            )
            when (result) {
                is Assets.Success -> {
                    val articles = result.data ?: emptyList()
                    if (normalizedCategory == "sports") {
                        _state.update { currentState ->
                            currentState.copy(
                                sportsArticles = articles,
                                isLoading = false,
                                error = null
                            )
                        }
                    } else if (normalizedCategory == "entertainment") {
                        _state.update { currentState ->
                            currentState.copy(
                                entertainmentArticles = articles,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                }

                is Assets.Error -> {
                    _state.update { currentState ->
                        currentState.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun getNewsArticles(
        category: String,
        countryCode: String,
        lang: String = state.value.selectedLanguage
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = repository.getTopHeadlines(
                country = countryCode,
                category = category,
                lang = lang
            )
            when (result) {
                is Assets.Success -> {
                    _state.update { currentState ->
                        currentState.copy(
                            articles = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                }

                is Assets.Error -> {
                    _state.update { currentState ->
                        currentState.copy(
                            error = result.message,
                            isLoading = false,
                            articles = emptyList()
                        )
                    }
                }
            }
        }
    }

    private fun searchForNews(query: String) {
        if (query.isEmpty()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = repository.searchRequest(query)) {
                is Assets.Success -> {
                    _state.update { currentState ->
                        currentState.copy(
                            articles = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                }

                is Assets.Error -> {
                    _state.update { currentState ->
                        currentState.copy(
                            error = result.message,
                            isLoading = false,
                            articles = emptyList()
                        )
                    }
                }
            }
        }
    }
}