package ru.unlim1x.wb_project.ui.viewmodels

import androidx.lifecycle.LiveData

interface ViewState<T> {
    fun viewState(): LiveData<T>
}