package ru.unlim1x.wb_project.ui.viewmodels


import androidx.lifecycle.ViewModel

abstract class MainViewModel<E, S> : ViewModel(), EventHandler<E>, ViewState<S>
