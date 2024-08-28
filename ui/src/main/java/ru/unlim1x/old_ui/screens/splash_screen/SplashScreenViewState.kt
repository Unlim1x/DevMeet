package ru.unlim1x.old_ui.screens.splash_screen

internal sealed class SplashScreenViewState {
    data object Init : SplashScreenViewState()
    data object Display : SplashScreenViewState()

    data class Finished(val isAuthorized: Boolean) : SplashScreenViewState()
}