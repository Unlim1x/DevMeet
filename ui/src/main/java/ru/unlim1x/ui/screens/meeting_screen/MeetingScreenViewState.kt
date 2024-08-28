package ru.unlim1x.ui.screens.meeting_screen

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Meeting

internal sealed class MeetingScreenViewState {
    data object Loading : MeetingScreenViewState()
    data class Display(
        val allMeetings: Flow<List<Meeting>>,
        val activeMeetings: Flow<List<Meeting>>
    ) : MeetingScreenViewState()

}