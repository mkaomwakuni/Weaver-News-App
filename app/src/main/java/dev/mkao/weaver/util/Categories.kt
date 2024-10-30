package dev.mkao.weaver.util

enum class NewsCategories(val apiValue: String, val displayName: String) {
    General("general", "General"),
    Nation("nation", "Nation"),
    Business("business", "Business"),
    Technology("technology", "Technology"),
    Entertainment("entertainment", "Entertainment"),
    Sports("sports", "Sports"),
    Science("science", "Science"),
    Health("health", "Health")
}