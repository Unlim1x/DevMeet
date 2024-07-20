package ru.unlim1x.wb_project.ui.viewmodels.profile_screen

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.screens.model.User

sealed class ProfileScreenViewState {
    data object Init:ProfileScreenViewState()
    data class Display(val user: User):ProfileScreenViewState()
}