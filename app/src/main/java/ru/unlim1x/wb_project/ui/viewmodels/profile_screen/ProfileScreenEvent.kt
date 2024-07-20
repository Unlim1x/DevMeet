package ru.unlim1x.wb_project.ui.viewmodels.profile_screen

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.screens.model.User

sealed class ProfileScreenEvent {
    data object OpenScreen:ProfileScreenEvent()

}