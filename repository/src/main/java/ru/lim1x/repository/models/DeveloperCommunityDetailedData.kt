package ru.lim1x.repository.models

import ru.lim1x.domain.models.Tag

data class DeveloperCommunityDetailedData(
    val id: Int,
    val name: String,
    val imageUri: Any,
    val tags: List<Tag>,
    val subscribers: List<Int>,
    val activeMeetings: List<Int>,
    val passedMeetings: List<Int>
)
