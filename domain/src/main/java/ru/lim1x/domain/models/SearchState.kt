package ru.lim1x.domain.models

data class SearchState(
    val searchedEvents: List<Event>,
    val rail: Rail,
    val additionHeader: String,
    val additionList: List<Event>
)