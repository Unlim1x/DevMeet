package ru.unlim1x.wb_project.ui.viewmodels.community_detailed_screen

sealed class CommunityDetailedScreenEvent {
    data class OpenScreen(val id:Int):CommunityDetailedScreenEvent()


}