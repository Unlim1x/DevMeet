package ru.unlim1x.old_ui.screens.auth_phone_input_screen

internal sealed class AuthPhoneInputScreenEvent {

    data class SendCode(val countryCode: String, val phoneNumber: String) :
        AuthPhoneInputScreenEvent()

}