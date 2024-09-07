package ru.lim1x.domain.models

data class Tag(
    val id: Int,
    val text: String,
    val isSelected: Boolean = false
)