package ru.unlim1x.wb_project.ui.viewmodels.bottom_bar

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.screens.model.User

sealed class BottomBarEvent {
    data object LoadBottomBar:BottomBarEvent()

}