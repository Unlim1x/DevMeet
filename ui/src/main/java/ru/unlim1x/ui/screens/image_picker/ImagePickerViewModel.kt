package ru.unlim1x.ui.screens.image_picker

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.screens.MainViewModel

internal class ImagePickerViewModel(private val context: Application) :
    MainViewModel<ImagePickerEvent, ImagePickerViewState>() {
    override val _viewState: MutableStateFlow<ImagePickerViewState> = MutableStateFlow(ImagePickerViewState.DisplayEmpty)


    override fun obtain(event: ImagePickerEvent) {
        when(event){
            ImagePickerEvent.Dismissed -> {}
            is ImagePickerEvent.OnImageClicked -> {
                Log.e("", "${event.imageUri}")}
            ImagePickerEvent.PermissionDenied -> {reduce(event, ImagePickerViewState.DisplayEmpty)}
            ImagePickerEvent.PermissionGranted -> {reduce(event as ImagePickerEvent.PermissionGranted, ImagePickerViewState.DisplayEmpty)}
        }
    }

    private fun reduce(event: ImagePickerEvent, state: ImagePickerViewState.DisplayEmpty){
        _viewState.update { ImagePickerViewState.PermissionNotGranted }
    }
    private fun reduce(event: ImagePickerEvent.PermissionGranted, state: ImagePickerViewState.DisplayEmpty){
        loadAllImages()
    }

    private fun getAllImages(): List<Uri>
    {
        val allImages = mutableListOf<Uri>()

        val imageProjection = arrayOf(
            MediaStore.Images.Media._ID
        )

        val imageSortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"


        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            null,
            null,
            imageSortOrder
        )

        cursor.use {

            if (cursor != null)
            {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (cursor.moveToNext())
                {
                    allImages.add(
                        ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(idColumn)
                        )
                    )
                }
            }
            else
            {
                Log.d("AddViewModel", "Cursor is null!")
            }
        }
        return allImages
    }

    fun loadAllImages()
    {
        viewModelScope.launch {
            _viewState.update { ImagePickerViewState.Display(getAllImages()) }
        }
    }


    override fun viewState(): StateFlow<ImagePickerViewState> {
        return _viewState
    }
}