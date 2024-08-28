package ru.unlim1x.ui.screens.community_detailed_screen

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.CommunityDetailed
import ru.lim1x.domain.models.Meeting

internal sealed class CommunityDetailedScreenViewState {
    data object Init : CommunityDetailedScreenViewState()
    data class Display(
        val community: Flow<CommunityDetailed>,
        val listOfMeetings: Flow<List<Meeting>>,
        val initial: CommunityDetailed
    ) : CommunityDetailedScreenViewState()

}