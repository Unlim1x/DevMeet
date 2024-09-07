package ru.unlim1x.ui.navigation.start_app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
internal fun StartScreen() {
    val navHostController: NavHostController = rememberNavController()
    StartAppNavGraph(navController = navHostController)
}