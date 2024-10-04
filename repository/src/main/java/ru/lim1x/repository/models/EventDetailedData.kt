package ru.lim1x.repository.models

import java.time.LocalDate

data class EventDetailedData(
    val name: String,
    val date: LocalDate,
    val shortAddress: String,
    val tags: List<String>,
    val id: Int,
    val url: String,
    val speaker: SpeakerData,
    val address: String,
    val attendees: List<Int>,
    val organizer: Int,
    val otherMeetings: List<Int>,
    val maxAttendeesQuantity: Int
)
