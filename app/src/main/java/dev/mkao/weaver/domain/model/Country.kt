package dev.mkao.weaver.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey val code: String,
    val name: String,
    val isSelected: Boolean = false
)