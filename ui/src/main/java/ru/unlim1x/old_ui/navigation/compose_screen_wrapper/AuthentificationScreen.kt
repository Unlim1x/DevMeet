package ru.unlim1x.old_ui.navigation.compose_screen_wrapper

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.old_ui.navigation.nav_graph.auth.AuthNavGraph

@Composable
internal fun AuthentificationScreen() {
    val navController = rememberNavController()
    AuthNavGraph(navController = navController)
}