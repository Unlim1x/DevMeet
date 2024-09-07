package ru.unlim1x.ui.screens.main_screen

import ru.lim1x.domain.models.CommunityRail
import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.Tag

internal sealed class MainScreenViewState {
    data object Loading : MainScreenViewState()
    data object Error : MainScreenViewState()
    data class Display(
        val mainEventsList: List<Event>,
        val soonEventsList: List<Event>,
        val railList: List<CommunityRail>,
        val otherTags: List<Tag>?,
        val infiniteEventsListByTag: List<Event>?,
        //todo: val personsToKnow:List<Person>?
    ) : MainScreenViewState()

    data class DisplaySearch(
        val searchedEventsList: List<Event>,
        val rail: CommunityRail,
        val soonEventsList: List<Event>,
    ) : MainScreenViewState()
}