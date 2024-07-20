package ru.unlim1x.wb_project.ui.viewmodels.auth_profile_screen


sealed class AuthProfileScreenViewState {

    data object Display:AuthProfileScreenViewState()
        data object Saved:AuthProfileScreenViewState()

}