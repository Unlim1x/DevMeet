package ru.unlim1x.ui.navigation.compose_screen_wrapper

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.ui.navigation.nav_graph.global.MainNavGraph

@Composable
internal fun MainScreen() {
    val navHostController: NavHostController = rememberNavController()
    MainNavGraph(navHostController)
}