package ru.unlim1x.wb_project.ui.viewmodels.community_screen

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Community

sealed class CommunityScreenViewState{

    data object Loading:CommunityScreenViewState()

    data class Display(val communities: Flow<List<Community>>):CommunityScreenViewState()

}
