package ru.unlim1x.wb_project.ui.viewmodels.auth_code_input_screen

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.screens.model.User

sealed class AuthCodeInputScreenEvent {

    data class Validate(val code:String):AuthCodeInputScreenEvent()
    data object Resend:AuthCodeInputScreenEvent()

}