package ru.unlim1x.wb_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AuthentificationScreen() {
    val navController = rememberNavController()
    AuthNavGraph(navController = navController)
}