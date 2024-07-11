package ru.unlim1x.wb_project.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.ui.navigation.AuthNavGraph

@Composable
fun AuthentificationScreen() {
    val navController = rememberNavController()
    AuthNavGraph(navController = navController)
}