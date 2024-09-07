package ru.lim1x.repository.models

import java.time.LocalDate

internal data class EventRepository(
    val name: String,
    val date: LocalDate,
    val place: String,
    val tags: List<String>,
    val id: Int,
    val url: String,
)