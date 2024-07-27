package ru.unlim1x.wb_project.ui.viewmodels.auth_profile_screen

sealed class AuthProfileScreenEvent {

    data class Save(val name:String, val surname:String):AuthProfileScreenEvent()

}