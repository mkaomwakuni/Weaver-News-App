package dev.mkao.weaver.domain.model.repository

import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.util.Assets

interface Repository {
	suspend fun getTopHeadlines(
		category: String
	) :Assets<List<Article>>
}