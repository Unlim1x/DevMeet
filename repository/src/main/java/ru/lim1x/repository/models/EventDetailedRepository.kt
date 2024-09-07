package ru.lim1x.repository.models

import java.time.LocalDate

data class EventDetailedRepository(
    val name: String,
    val date: LocalDate,
    val place: String,
    val tags: List<String>,
    val id: Int,
    val url: String,
    val speaker: SpeakerRepository,
    val address: String,
    val attendees: List<Int>,
    val organizer: Int,
    val otherMeetings: List<Int>
)
