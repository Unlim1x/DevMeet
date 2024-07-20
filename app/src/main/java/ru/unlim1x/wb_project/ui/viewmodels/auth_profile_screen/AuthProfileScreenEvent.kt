package ru.unlim1x.wb_project.ui.viewmodels.auth_profile_screen

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.screens.model.User

sealed class AuthProfileScreenEvent {

    data class Save(val name:String, val surname:String):AuthProfileScreenEvent()

}