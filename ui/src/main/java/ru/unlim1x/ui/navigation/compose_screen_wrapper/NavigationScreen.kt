package ru.unlim1x.ui.navigation.compose_screen_wrapper

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.ui.navigation.nav_graph.bottom.BottomNavGraph
import ru.unlim1x.wb_project.ui.screens.bottom_bar.BottomBar

@Composable
internal fun NavigationScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomBar(navController = navController) }) {
        BottomNavGraph(navController = navController, it.calculateBottomPadding())
    }
}

