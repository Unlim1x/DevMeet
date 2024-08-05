package ru.unlim1x.wb_project.ui.viewmodels.profile_screen

import android.net.Uri

sealed class ProfileScreenEvent {
    data object OpenScreen : ProfileScreenEvent()

    data class UserChoseImage(val uri: Uri):ProfileScreenEvent()

}