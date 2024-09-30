package ru.unlim1x.ui.screens.main_screen

import ru.lim1x.domain.models.Rail
import ru.unlim1x.ui.kit.tag.TagUi
import ru.unlim1x.ui.models.EventUI

internal sealed class MainScreenViewState {
    data object Loading : MainScreenViewState()
    data object Error : MainScreenViewState()
    data class Display(
        val mainEventsList: List<EventUI>,
        val soonEventsList: List<EventUI>,
        val railList: List<Rail>,
        val otherTags: List<TagUi>,
        val infiniteEventsListByTag: List<EventUI>,
        //todo: val personsToKnow:List<Person>?
    ) : MainScreenViewState()

    data class DisplaySearch(
        val searchedEventsList: List<EventUI>,
        val rail: Rail,
        val eventsRail: List<EventUI>,
        val eventsRailHeader: String
    ) : MainScreenViewState()
}