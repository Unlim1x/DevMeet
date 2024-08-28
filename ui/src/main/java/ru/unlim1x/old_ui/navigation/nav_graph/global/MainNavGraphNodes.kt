package ru.unlim1x.old_ui.navigation.nav_graph.global

internal enum class MainNavGraphNodes {
    SplashScreen,
    AuthScreen,
    MainScreen;

    val route = this.name
}