package ru.unlim1x.wb_project.ui.viewmodels.community_detailed_screen

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.screens.model.User

sealed class CommunityDetailedScreenEvent {
    data object OpenScreen:CommunityDetailedScreenEvent()


}