package ru.unlim1x.wb_project.ui.viewmodels.meeting_detailed

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.screens.model.User

sealed class MeetingDetailedScreenEvent {
    data object OpenScreen:MeetingDetailedScreenEvent()

    data object WillGo:MeetingDetailedScreenEvent()
    data object WillNotGo:MeetingDetailedScreenEvent()


}