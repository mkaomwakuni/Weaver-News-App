package dev.mkao.weaver.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
	val id: String,
	val name: String?,
	val category: String?,
	val url: String?
): Parcelable