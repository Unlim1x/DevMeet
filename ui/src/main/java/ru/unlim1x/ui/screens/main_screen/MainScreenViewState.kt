package ru.unlim1x.ui.screens.main_screen

import ru.lim1x.domain.models.CommunityRail
import ru.lim1x.domain.models.Tag
import ru.unlim1x.ui.kit.tag.TagUi
import ru.unlim1x.ui.models.EventUI
import ru.unlim1x.ui.models.CommunityRailUI

internal sealed class MainScreenViewState {
    data object Loading : MainScreenViewState()
    data object Error : MainScreenViewState()
    data class Display(
        val mainEventsList: List<EventUI>,
        val soonEventsList: List<EventUI>,
        val railList: List<CommunityRailUI>,
        val otherTags: List<TagUi>,
        val infiniteEventsListByTag: List<EventUI>,
        //todo: val personsToKnow:List<Person>?
    ) : MainScreenViewState()

    data class DisplaySearch(
        val searchedEventsList: List<EventUI>,
        val rail: CommunityRail,
        val soonEventsList: List<EventUI>,
    ) : MainScreenViewState()
}