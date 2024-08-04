package ru.unlim1x.wb_project.ui.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.ui.screens.BottomBar

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomBar(navController = navController) }) {
        BottomNavGraph(navController = navController, it.calculateBottomPadding())
    }
}

