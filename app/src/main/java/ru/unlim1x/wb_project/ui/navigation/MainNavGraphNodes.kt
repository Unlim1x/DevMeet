package ru.unlim1x.wb_project.ui.navigation

sealed class MainNavGraphNodes(val route : String) {
    data object SplashScreen:MainNavGraphNodes(route = "SplashScreen")
    data object AuthScreen:MainNavGraphNodes(route = "AuthScreen")
    data object MainScreen:MainNavGraphNodes(route = "MainScreen")
}