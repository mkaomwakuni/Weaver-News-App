package dev.mkao.weaver.domain.model

sealed class EventsHolder{
	data class OnArticleCardClicked(val article: Article): EventsHolder()
	data class OnCategoryClicked(val category: String): EventsHolder()
	data class OnSearchCategoryChanged(val searchRequest: String) : EventsHolder()
	object OnSearchIconClicked: EventsHolder()
	object OnCloseIconClicked: EventsHolder()
}
