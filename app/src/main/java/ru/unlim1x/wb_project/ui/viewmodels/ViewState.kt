package ru.unlim1x.wb_project.ui.viewmodels

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow

interface ViewState<T> {
    fun viewState(): StateFlow<T>
}