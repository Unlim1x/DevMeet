package ru.unlim1x.ui.navigation.start_app

internal enum class StartAppNavGraphNodes {
    Splash,
    Onboarding,
    GeoOnboarding;

    val route = this.name
}