package ru.unlim1x.wb_project.ui.viewmodels.image_picker

import android.net.Uri

sealed class ImagePickerViewState {
    data object PermissionNotGranted:ImagePickerViewState()
    data object RequestPermission:ImagePickerViewState()
    data class Display(val listOfImagesUris:List<Uri>):ImagePickerViewState()
    data object DisplayEmpty : ImagePickerViewState()
}