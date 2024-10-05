package ru.unlim1x.ui.navigation.start_app

internal enum class StartAppNavGraphNodes {
    Splash,
    Onboarding,
    Main,
    DetailedEvent;

    val route = this.name
    fun routeId() = "$route/{id}"
    fun routeId(id: Int) = "$route/$id"
}