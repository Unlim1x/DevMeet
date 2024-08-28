package ru.unlim1x.ui.screens

import kotlinx.coroutines.flow.StateFlow

internal interface ViewState<T> {
    fun viewState(): StateFlow<T>
}