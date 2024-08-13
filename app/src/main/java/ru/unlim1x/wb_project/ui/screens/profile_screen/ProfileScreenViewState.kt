package ru.unlim1x.wb_project.ui.screens.profile_screen

import ru.lim1x.domain.models.User

internal sealed class ProfileScreenViewState {
    data object Init : ProfileScreenViewState()
    data class Display(val user: User) : ProfileScreenViewState()
}