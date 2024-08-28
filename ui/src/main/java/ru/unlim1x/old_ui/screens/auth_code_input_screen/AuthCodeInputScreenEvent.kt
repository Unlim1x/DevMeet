package ru.unlim1x.old_ui.screens.auth_code_input_screen

internal sealed class AuthCodeInputScreenEvent {

    data class Validate(val phone: String, val code: String) : AuthCodeInputScreenEvent()
    data object Resend : AuthCodeInputScreenEvent()

}