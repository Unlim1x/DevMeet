package ru.unlim1x.wb_project.ui.viewmodels.meeting_screen

sealed class MeetingScreenEvent {
    data object OpenScreen : MeetingScreenEvent()

}