package ru.unlim1x.wb_project.ui.viewmodels

import kotlinx.coroutines.flow.StateFlow

interface ViewState<T> {
    fun viewState(): StateFlow<T>
}