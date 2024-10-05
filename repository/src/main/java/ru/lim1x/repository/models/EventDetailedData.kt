package ru.lim1x.repository.models

import java.time.LocalDate

internal data class EventDetailedData(
    val name: String,
    val description: String,
    val date: LocalDate,
    val shortAddress: String,
    val tags: List<String>,
    val id: Int,
    val url: String,
    val speaker: SpeakerData,
    val address: String,
    val attendees: List<PersonData>,
    val organizer: OrganizerData,
    val otherMeetings: List<EventData>,
    val maxAttendeesQuantity: Int
)
