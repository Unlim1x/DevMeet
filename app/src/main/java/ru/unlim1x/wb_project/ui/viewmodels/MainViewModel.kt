package ru.unlim1x.wb_project.ui.viewmodels


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class MainViewModel<E, S> : ViewModel(), EventHandler<E>, ViewState<S>{
    protected abstract val _viewState : MutableStateFlow<S>
}
