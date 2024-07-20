package ru.unlim1x.wb_project.ui.viewmodels.auth_phone_input_screen

import ru.unlim1x.wb_project.ui.uiKit.custominputview.model.Country

sealed class AuthPhoneInputScreenViewState {
    data object Display:AuthPhoneInputScreenViewState()

    data class Sent(val countryCode: String, val phone:String):AuthPhoneInputScreenViewState()

}