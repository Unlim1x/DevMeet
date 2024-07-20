package ru.unlim1x.wb_project.ui.viewmodels.community_detailed_screen

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.screens.model.User
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Community
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Meeting

sealed class CommunityDetailedScreenViewState {
    data object Init:CommunityDetailedScreenViewState()
    data class Display(val community: Community, val listOfMeetings:List<Meeting>):CommunityDetailedScreenViewState()

}