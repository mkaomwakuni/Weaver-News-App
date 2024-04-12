package dev.mkao.weaver.domain.model

import androidx.room.PrimaryKey

data class Article(
	val source: Source,
	val author: String?,
	val title: String,
	val content: String?,
	val description: String?,
	@PrimaryKey val url: String,
	val urlToImage: String?,
	val publishedAt: String,
)