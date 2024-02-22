package dev.mkao.weaver.presentation.common

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

fun DateFormat(inputDateTime: String?):String {
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