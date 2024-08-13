package ru.unlim1x.wb_project.ui.screens.auth_phone_input_screen

internal sealed class AuthPhoneInputScreenEvent {

    data class SendCode(val countryCode: String, val phoneNumber: String) :
        AuthPhoneInputScreenEvent()

}