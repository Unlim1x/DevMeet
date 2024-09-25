package ru.unlim1x.ui.screens.main_screen

internal sealed class MainScreenEvent {
    data class ClickOnEvent(val eventId: Int) : MainScreenEvent()
    data class ClickOnCommunity(val communityId: Int) : MainScreenEvent()

    data class ClickOnCommunitySubscribe(val communityId: Int) : MainScreenEvent()

    data object ScrolledToEndOfList : MainScreenEvent()

    data class ClickOnTag(val tagId: Int) : MainScreenEvent()

    data object Idle : MainScreenEvent()
}