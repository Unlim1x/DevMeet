package ru.unlim1x.ui.navigation.start_app

internal enum class StartAppNavGraphNodes {
    Splash,
    Onboarding,
    Main;

    val route = this.name
}