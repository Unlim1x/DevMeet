package ru.unlim1x.wb_project.ui.navigation.nav_graph.global

internal enum class MainNavGraphNodes {
    SplashScreen,
    AuthScreen,
    MainScreen;

    val route = this.name
}