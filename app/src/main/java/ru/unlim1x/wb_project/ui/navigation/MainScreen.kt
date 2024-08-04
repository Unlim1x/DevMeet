package ru.unlim1x.wb_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navHostController: NavHostController = rememberNavController()
    MainNavGraph(navHostController)
}