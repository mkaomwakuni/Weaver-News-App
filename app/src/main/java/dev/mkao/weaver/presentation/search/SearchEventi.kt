package dev.mkao.weaver.presentation.search

sealed class SearchEvent {
    data class UpdateSearchQuery(val query: String) : SearchEvent()
    object ClearSearchQuery : SearchEvent()
    object SearchNews : SearchEvent()
    data class ChangeCategory(val category: String) : SearchEvent()
}