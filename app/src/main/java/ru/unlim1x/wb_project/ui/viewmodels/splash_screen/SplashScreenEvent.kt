package ru.unlim1x.wb_project.ui.viewmodels.splash_screen

sealed class SplashScreenEvent {
    data object OpenApp:SplashScreenEvent()

}