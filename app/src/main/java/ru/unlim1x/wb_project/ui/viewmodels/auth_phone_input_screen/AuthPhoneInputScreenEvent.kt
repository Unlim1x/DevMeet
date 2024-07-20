package ru.unlim1x.wb_project.ui.viewmodels.auth_phone_input_screen

import kotlinx.coroutines.flow.Flow
import ru.unlim1x.wb_project.ui.screens.model.User
import ru.unlim1x.wb_project.ui.uiKit.custominputview.model.Country

sealed class AuthPhoneInputScreenEvent {

    data class SendCode(val countryCode: String, val phoneNumber:String):AuthPhoneInputScreenEvent()

}