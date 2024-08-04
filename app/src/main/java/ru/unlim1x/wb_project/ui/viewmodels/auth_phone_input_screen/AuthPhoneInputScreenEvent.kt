package ru.unlim1x.wb_project.ui.viewmodels.auth_phone_input_screen

sealed class AuthPhoneInputScreenEvent {

    data class SendCode(val countryCode: String, val phoneNumber: String) :
        AuthPhoneInputScreenEvent()

}