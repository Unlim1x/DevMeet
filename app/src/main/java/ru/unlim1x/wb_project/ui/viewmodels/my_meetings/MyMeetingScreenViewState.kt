package ru.unlim1x.wb_project.ui.viewmodels.my_meetings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.models.Meeting

sealed class MyMeetingScreenViewState{
    data object Loading:MyMeetingScreenViewState()
    data class Display(val plannedMeetings: StateFlow<List<Meeting>>,
                       val finishedMeetings: StateFlow<List<Meeting>>):MyMeetingScreenViewState()

}