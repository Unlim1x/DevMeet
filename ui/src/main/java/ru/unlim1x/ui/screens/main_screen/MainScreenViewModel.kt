package ru.unlim1x.ui.screens.main_screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.unlim1x.old_ui.screens.MainViewModel

internal class MainScreenViewModel() : MainViewModel<MainScreenEvent, MainScreenViewState>() {
    override fun obtain(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.ClickOnCommunity -> TODO()
            is MainScreenEvent.ClickOnCommunitySubscribe -> TODO()
            is MainScreenEvent.ClickOnEvent -> TODO()
            MainScreenEvent.ScrolledToEndOfList -> TODO()
        }
    }

    override val _viewState: MutableStateFlow<MainScreenViewState> =
        MutableStateFlow(MainScreenViewState.Loading)

    override fun viewState(): StateFlow<MainScreenViewState> {
        return _viewState
    }
}