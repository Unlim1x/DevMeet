package ru.unlim1x.wb_project.ui.screens


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

internal abstract class MainViewModel<E, S> : ViewModel(), EventHandler<E>, ViewState<S> {
    protected abstract val _viewState : MutableStateFlow<S>
}
