package ru.unlim1x.wb_project.ui.viewmodels.community_screen

sealed class CommunityScreenEvent {
    data object OpenScreen : CommunityScreenEvent()
}