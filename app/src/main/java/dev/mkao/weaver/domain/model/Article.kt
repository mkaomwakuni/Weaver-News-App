package dev.mkao.weaver.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Articles")
data class Article(
	val source: Source,
	val author: String?,
	val title: String,
	val content: String?,
	val description: String?,
	@PrimaryKey val url: String,
	val urlToImage: String?,
	val publishedAt: String,
): Parcelable