package ru.unlim1x.old_ui.screens

import kotlinx.coroutines.flow.StateFlow

internal interface ViewState<T> {
    fun viewState(): StateFlow<T>
}