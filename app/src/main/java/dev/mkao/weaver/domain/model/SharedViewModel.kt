package dev.mkao.weaver.domain.model
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    fun selectArticle(article: Article) {
        viewModelScope.launch {
            _selectedArticle.value = article
        }
    }
    fun selectCategory(category: String) {
        viewModelScope.launch {
            _selectedCategory.value = category
        }
    }
}