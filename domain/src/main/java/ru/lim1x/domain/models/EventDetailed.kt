package ru.lim1x.domain.models

import java.time.LocalDate

data class EventDetailed(
    val name: String,
    val description: String,
    val date: LocalDate,
    val shortAddress: String,
    val tags: List<Tag>,
    val id: Int,
    val url: String,
    val mapUrl: String,
    val speaker: Speaker,
    val address: String,
    val nearestSubway: String,
    val latitude: Float? = null,
    val longitude: Float? = null,
    val attendees: List<Person>,
    val organizer: Organizer,
    val otherMeetings: List<Event>
)
