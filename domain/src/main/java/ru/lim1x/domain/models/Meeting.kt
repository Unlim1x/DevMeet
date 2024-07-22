package ru.lim1x.domain.models
typealias IdAndAvatar = Pair<Int,String>

data class Meeting(
    val name: String,
    val timeAndPlace: TimeAndPlace,
    val isFinished: Boolean,
    val tags: List<String>,
    val id: Int
)
