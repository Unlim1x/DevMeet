package ru.unlim1x.wb_project.ui.viewmodels.meeting_detailed

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.screens.model.User
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Meeting

sealed class MeetingDetailedScreenViewState {
    data object Init:MeetingDetailedScreenViewState()
    data class DisplayGo(val meeting: Meeting, val comingAvatars:List<String>, val go:Boolean):MeetingDetailedScreenViewState()

    data class DisplayNotGo(val meeting: Meeting, val comingAvatars:List<String>, val go:Boolean):MeetingDetailedScreenViewState()
}