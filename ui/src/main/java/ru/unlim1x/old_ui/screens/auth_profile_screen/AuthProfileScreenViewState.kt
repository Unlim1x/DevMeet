package ru.unlim1x.old_ui.screens.auth_profile_screen


internal sealed class AuthProfileScreenViewState {

    data object Display : AuthProfileScreenViewState()
    data object Saved : AuthProfileScreenViewState()

}