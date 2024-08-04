package ru.unlim1x.wb_project.ui.viewmodels.meeting_screen

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Meeting

sealed class MeetingScreenViewState {
    data object Loading : MeetingScreenViewState()
    data class Display(
        val allMeetings: Flow<List<Meeting>>,
        val activeMeetings: Flow<List<Meeting>>
    ) : MeetingScreenViewState()

}