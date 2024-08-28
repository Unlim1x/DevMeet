package ru.unlim1x.old_ui.navigation.compose_screen_wrapper

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.old_ui.navigation.nav_graph.global.MainNavGraph

@Composable
internal fun MainScreen() {
    val navHostController: NavHostController = rememberNavController()
    MainNavGraph(navHostController)
}