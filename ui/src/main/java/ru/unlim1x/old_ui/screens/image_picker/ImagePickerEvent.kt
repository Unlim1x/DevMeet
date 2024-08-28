package ru.unlim1x.old_ui.screens.image_picker

import android.net.Uri

internal sealed class ImagePickerEvent {
    data class OnImageClicked(val imageUri: Uri) : ImagePickerEvent()

    data object Dismissed:ImagePickerEvent()

    data object PermissionGranted:ImagePickerEvent()
    data object PermissionDenied:ImagePickerEvent()
}