package dev.mkao.weaver.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Articles")
data class Article(
    @PrimaryKey val url: String,
    val source: Source,
    val author: String?,
    val title: String,
    val content: String?,
    val description: String?,
    var isBookedMarked: Boolean = false,
    val image: String?,
    val publishedAt: String,
) : Parcelable