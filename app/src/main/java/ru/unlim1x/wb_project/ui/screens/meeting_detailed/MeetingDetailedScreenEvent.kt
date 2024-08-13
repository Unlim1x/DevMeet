package ru.unlim1x.wb_project.ui.screens.meeting_detailed

internal sealed class MeetingDetailedScreenEvent {

    data class LoadScreen(val meetingId: Int) : MeetingDetailedScreenEvent()
    data class WillGo(val meetingId: Int) : MeetingDetailedScreenEvent()
    data class WillNotGo(val meetingId: Int) : MeetingDetailedScreenEvent()


}