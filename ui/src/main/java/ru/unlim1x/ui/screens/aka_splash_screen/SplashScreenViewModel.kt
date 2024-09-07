package ru.unlim1x.ui.screens.aka_splash_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class SplashScreenViewModel() : ViewModel() {
    private val _locationPermissionState: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val locationPermissionState: StateFlow<Boolean?> = _locationPermissionState

    // Функция, вызываемая для запроса разрешения
    fun onPermissionRequested(isGranted: Boolean) {
        _locationPermissionState.value = isGranted
    }
}