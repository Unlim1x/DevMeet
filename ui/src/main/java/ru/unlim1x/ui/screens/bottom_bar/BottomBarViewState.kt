package ru.unlim1x.ui.screens.bottom_bar

import ru.unlim1x.wb_project.ui.navigation.nav_graph.bottom.NavGraphNodes

internal sealed class BottomBarViewState {
    data object Init : BottomBarViewState()
    data class Display(val roots: List<NavGraphNodes>, val overengineering: Int) :
        BottomBarViewState()
}