package ru.unlim1x.wb_project.ui.viewmodels.meeting_detailed


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.models.MeetingDetailedExt
import java.lang.Thread.State

sealed class MeetingDetailedScreenViewState {

    data object Loading:MeetingDetailedScreenViewState()
    data object Error:MeetingDetailedScreenViewState()
    data class Display(val meeting: StateFlow<MeetingDetailedExt?>, val listAvatars:StateFlow<List<String>>, val go:Boolean,  val initial : MeetingDetailedExt?):MeetingDetailedScreenViewState()

}