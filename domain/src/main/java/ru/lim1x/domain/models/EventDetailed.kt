package ru.lim1x.domain.models

import java.time.LocalDate

data class EventDetailed(
    val name: String,
    val date: LocalDate,
    val place: String,
    val tags: List<String>,
    val id: Int,
    val url: String,
    val speaker: Speaker,
    val address: String,
    val attendees: List<Int>,
    val organizer: Int,
    val otherMeetings: List<Int>
)
