package ru.unlim1x.wb_project.ui.screens.profile_screen

import android.net.Uri

internal sealed class ProfileScreenEvent {
    data object OpenScreen : ProfileScreenEvent()

    data class UserChoseImage(val uri: Uri):ProfileScreenEvent()

}