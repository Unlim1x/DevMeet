package ru.unlim1x.wb_project.ui.viewmodels.splash_screen

sealed class SplashScreenViewState {
    data object Init : SplashScreenViewState()
    data object Display : SplashScreenViewState()

    data class Finished(val isAuthorized: Boolean) : SplashScreenViewState()
}