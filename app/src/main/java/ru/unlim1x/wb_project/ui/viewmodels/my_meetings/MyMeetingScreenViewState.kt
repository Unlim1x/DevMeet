package ru.unlim1x.wb_project.ui.viewmodels.my_meetings

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Meeting

sealed class MyMeetingScreenViewState{
    data object Loading:MyMeetingScreenViewState()
    data class Display(val plannedMeetings: Flow<List<Meeting>>,
                       val finishedMeetings: Flow<List<Meeting>>):MyMeetingScreenViewState()

}