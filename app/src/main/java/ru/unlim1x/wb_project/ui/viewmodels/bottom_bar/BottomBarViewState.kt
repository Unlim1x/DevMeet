package ru.unlim1x.wb_project.ui.viewmodels.bottom_bar

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.screens.model.User

sealed class BottomBarViewState {
    data object Init:BottomBarViewState()
    data class Display(val roots:List<NavGraphNodes>, val overengineering: Int):BottomBarViewState()
}