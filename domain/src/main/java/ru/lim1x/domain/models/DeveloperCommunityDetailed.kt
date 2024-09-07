package ru.lim1x.domain.models

data class DeveloperCommunityDetailed(
    val id: Int,
    val name: String,
    val imageUri: Any,
    val tags: List<Tag>,
    val subscribers: List<Int>,
    val activeMeetings: List<Int>,
    val passedMeetings: List<Int>
)
