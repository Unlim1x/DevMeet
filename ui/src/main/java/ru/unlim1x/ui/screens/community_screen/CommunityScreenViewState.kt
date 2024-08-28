package ru.unlim1x.ui.screens.community_screen

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Community

internal sealed class CommunityScreenViewState {

    data object Loading : CommunityScreenViewState()

    data class Display(val communities: Flow<List<Community>>) : CommunityScreenViewState()

}
