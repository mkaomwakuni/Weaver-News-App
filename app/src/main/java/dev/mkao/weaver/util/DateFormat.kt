package dev.mkao.weaver.util

import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

fun dateFormat(inputDateTime: String?):String {
	val inputFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val outputFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.LONG)
        .withLocale(Locale.getDefault())
    val dateString = try {
        val dateTime = OffsetDateTime.parse(inputDateTime,inputFormat)
        dateTime.format(outputFormatter)
    }catch (e:Exception){
        ""
    }
    return dateString
}
fun calculateElapsedTime(publishedAt: String?): String {
    return try {
        val publishedDateTime = OffsetDateTime.parse(publishedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val currentDateTime = OffsetDateTime.now()
        val duration = Duration.between(publishedDateTime, currentDateTime)
        when {
            duration.toDays() > 0 -> "${duration.toDays()} day ago"
            duration.toHours() > 0 -> "${duration.toHours()} hours ago"
            duration.toMinutes() > 0 -> "${duration.toMinutes()} minutes ago"
            else -> "Just now"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "Unknown time"
    }
}
