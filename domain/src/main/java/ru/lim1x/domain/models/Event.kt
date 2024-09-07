package ru.lim1x.domain.models

import java.time.LocalDate

data class Event(
    val name: String,
    val date: LocalDate,
    val place: String,
    val tags: List<String>,
    val id: Int,
    val url: String,
    val description: String,
)