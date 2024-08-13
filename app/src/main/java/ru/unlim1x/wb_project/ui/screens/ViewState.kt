package ru.unlim1x.wb_project.ui.screens

import kotlinx.coroutines.flow.StateFlow

internal interface ViewState<T> {
    fun viewState(): StateFlow<T>
}