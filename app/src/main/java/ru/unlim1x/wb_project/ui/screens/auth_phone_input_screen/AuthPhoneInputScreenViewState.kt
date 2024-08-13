package ru.unlim1x.wb_project.ui.screens.auth_phone_input_screen

internal sealed class AuthPhoneInputScreenViewState {
    data object Display : AuthPhoneInputScreenViewState()

    data class Sent(val countryCode: String, val phone: String) : AuthPhoneInputScreenViewState()

}