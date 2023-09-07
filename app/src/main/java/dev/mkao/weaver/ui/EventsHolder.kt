package dev.mkao.weaver.ui

import dev.mkao.weaver.domain.model.Article

sealed class EventsHolder{
	data class ArticleCardClicked(val article: Article)
	data class OnCategoryClicked(val category: String)
	data class OnSearchCategoryChanged(val searchRequest: String) : EventsHolder()
	object onSearchIconClicked: EventsHolder()
	object onCloseIconClicked:EventsHolder()
	
}
