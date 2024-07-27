package ru.unlim1x.wb_project.ui.viewmodels.meeting_detailed


import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.MeetingDetailedExt

sealed class MeetingDetailedScreenViewState {
    data object Init:MeetingDetailedScreenViewState()
    data class DisplayGo(val meeting: Flow<MeetingDetailedExt>, val listAvatars:Flow<List<String>>, val go:Boolean, val initial : MeetingDetailedExt):MeetingDetailedScreenViewState()

    data class DisplayNotGo(val meeting: Flow<MeetingDetailedExt>, val listAvatars:Flow<List<String>>, val go:Boolean,val initial : MeetingDetailedExt):MeetingDetailedScreenViewState()
}