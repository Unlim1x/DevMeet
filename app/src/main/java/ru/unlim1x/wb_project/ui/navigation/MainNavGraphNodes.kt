package ru.unlim1x.wb_project.ui.navigation

enum class MainNavGraphNodes {
    SplashScreen,
    AuthScreen,
    MainScreen;

    val route = this.name
}