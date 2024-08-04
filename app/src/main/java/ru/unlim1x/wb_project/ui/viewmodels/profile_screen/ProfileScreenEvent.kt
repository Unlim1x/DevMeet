package ru.unlim1x.wb_project.ui.viewmodels.profile_screen

sealed class ProfileScreenEvent {
    data object OpenScreen : ProfileScreenEvent()

}