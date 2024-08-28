package ru.unlim1x.old_ui.screens.meeting_detailed


import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.models.MeetingDetailedExt

internal sealed class MeetingDetailedScreenViewState {

    data object Loading : MeetingDetailedScreenViewState()
    data object Error : MeetingDetailedScreenViewState()
    data class Display(
        val meeting: StateFlow<MeetingDetailedExt?>,
        val listAvatars: StateFlow<List<String>>,
        val go: Boolean,
        val initial: MeetingDetailedExt?
    ) : MeetingDetailedScreenViewState()

}