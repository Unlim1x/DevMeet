package ru.unlim1x.wb_project.ui.screens.auth_profile_screen

internal sealed class AuthProfileScreenEvent {

    data class Save(val name: String, val surname: String) : AuthProfileScreenEvent()

}