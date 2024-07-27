package ru.unlim1x.wb_project.ui.viewmodels.bottom_bar

import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes

sealed class BottomBarViewState {
    data object Init:BottomBarViewState()
    data class Display(val roots:List<NavGraphNodes>, val overengineering: Int):BottomBarViewState()
}