package ru.unlim1x.old_ui.screens.bottom_bar

import ru.unlim1x.old_ui.navigation.nav_graph.bottom.NavGraphNodes

internal sealed class BottomBarViewState {
    data object Init : BottomBarViewState()
    data class Display(val roots: List<NavGraphNodes>, val overengineering: Int) :
        BottomBarViewState()
}