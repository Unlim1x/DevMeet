package ru.unlim1x.wb_project.ui.viewmodels.auth_code_input_screen

sealed class AuthCodeInputScreenEvent {

    data class Validate(val phone: String, val code: String) : AuthCodeInputScreenEvent()
    data object Resend : AuthCodeInputScreenEvent()

}