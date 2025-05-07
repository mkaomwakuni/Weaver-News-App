package dev.mkao.weaver.presentation.languages

import dev.mkao.weaver.domain.model.Edition

object LanguageConstants {
    val languages = listOf(
        Edition("ar", "Arabic", "العربية", listOf("EG", "SA")),
        Edition("zh", "Chinese", "中文", listOf("CN", "HK", "SG")),
        Edition("nl", "Dutch", "Nederlands", listOf("BE", "NL")),
        Edition("en", "English", "English", listOf("US", "GB", "CA", "IE", "NG", "PH", "SG")),
        Edition("fr", "French", "Français", listOf("FR", "CA", "BE", "CH")),
        Edition("de", "German", "Deutsch", listOf("DE", "AT", "BE", "CH")),
        Edition("el", "Greek", "Ελληνικά", listOf("GR")),
        Edition("he", "Hebrew", "עברית", listOf("IL")),
        Edition("hi", "Hindi", "हिन्दी", listOf("IN")),
        Edition("it", "Italian", "Italiano", listOf("IT", "CH")),
        Edition("ja", "Japanese", "日本語", listOf("JP")),
        Edition("ml", "Malayalam", "മലയാളം", listOf("IN")),
        Edition("mr", "Marathi", "मराठी", listOf("IN")),
        Edition("no", "Norwegian", "Norsk", listOf("NO")),
        Edition("pt", "Portuguese", "Português", listOf("BR", "PT")),
        Edition("ro", "Romanian", "Română", listOf("RO")),
        Edition("ru", "Russian", "Русский", listOf("RU")),
        Edition("es", "Spanish", "Español", listOf("AR", "MX")),
        Edition("sv", "Swedish", "Svenska", listOf("SE")),
        Edition("ta", "Tamil", "தமிழ்", listOf("IN", "SG")),
        Edition("te", "Telugu", "తెలుగు", listOf("IN")),
        Edition("uk", "Ukrainian", "Українська", listOf("UA"))
    )

    val countryMap = mapOf(
        "US" to "United States",
        "GB" to "United Kingdom",
        "CA" to "Canada",
        "IE" to "Ireland",
        "NG" to "Nigeria",
        "PH" to "Philippines",
        "SG" to "Singapore",
        "FR" to "France",
        "BE" to "Belgium",
        "CH" to "Switzerland",
        "DE" to "Germany",
        "AT" to "Austria",
        "EG" to "Egypt",
        "SA" to "Saudi Arabia",
        "CN" to "China",
        "HK" to "Hong Kong",
        "NL" to "Netherlands",
        "GR" to "Greece",
        "IL" to "Israel",
        "IN" to "India",
        "IT" to "Italy",
        "JP" to "Japan",
        "NO" to "Norway",
        "BR" to "Brazil",
        "PT" to "Portugal",
        "RO" to "Romania",
        "RU" to "Russia",
        "AR" to "Argentina",
        "MX" to "Mexico",
        "SE" to "Sweden",
        "UA" to "Ukraine"
    )
}
