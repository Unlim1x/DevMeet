package ru.lim1x.domain.models

data class MainState(
    val mainEventsList: List<Event>,
    val soonEventsList: List<Event>,
    val railList: List<Rail>,
    val otherTags: List<Tag>,
    val infiniteEventsListByTag: List<Event>,
)
