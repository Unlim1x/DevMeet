package ru.unlim1x.wb_project.ui.viewmodels.bottom_bar

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class BottomBarViewModel() : MainViewModel<BottomBarEvent, BottomBarViewState>() {

    override val _viewState: MutableStateFlow<BottomBarViewState> =
        MutableStateFlow(BottomBarViewState.Init)

    init {
        viewModelScope.launch {
            val roots = listOf(
                NavGraphNodes.MeetingRoot,
                NavGraphNodes.CommunityRoot,
                NavGraphNodes.MoreRoot
            )
            _viewState.value = (BottomBarViewState.Display(roots = roots, overengineering = 0))
        }
    }

    override fun obtain(event: BottomBarEvent) {
        when (event) {
            BottomBarEvent.LoadBottomBar -> {
                reduce(event, BottomBarViewState.Init)
            }
        }
    }

    private fun reduce(event: BottomBarEvent, state: BottomBarViewState.Init) {
        when (event) {
            BottomBarEvent.LoadBottomBar -> {
            }
        }
    }

    override fun viewState(): MutableStateFlow<BottomBarViewState> {
        return _viewState
    }

}