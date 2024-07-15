package dev.mkao.weaver.domain.model


data class Country(
    val code: String,
    val name: String,
    val isSelected: Boolean = false
)