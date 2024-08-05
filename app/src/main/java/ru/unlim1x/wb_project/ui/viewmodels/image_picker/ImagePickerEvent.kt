package ru.unlim1x.wb_project.ui.viewmodels.image_picker

import android.net.Uri

sealed class ImagePickerEvent {
    data class OnImageClicked(val imageUri: Uri) : ImagePickerEvent()

    data object Dismissed:ImagePickerEvent()

    data object PermissionGranted:ImagePickerEvent()
    data object PermissionDenied:ImagePickerEvent()
}