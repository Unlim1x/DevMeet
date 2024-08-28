package ru.unlim1x.old_ui.screens.community_detailed_screen

internal sealed class CommunityDetailedScreenEvent {
    data class OpenScreen(val id: Int) : CommunityDetailedScreenEvent()


}