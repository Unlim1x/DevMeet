package ru.unlim1x.wb_project.ui.viewmodels.community_screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetCommunitiesUseCase
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel


class CommunityScreenViewModel(
    private val getCommunitiesUseCase: IGetCommunitiesUseCase
) : MainViewModel<CommunityScreenEvent, CommunityScreenViewState>() {

    override val _viewState: MutableStateFlow<CommunityScreenViewState> =
        MutableStateFlow(CommunityScreenViewState.Loading)

    private fun reduce(event: CommunityScreenEvent, state: CommunityScreenViewState.Loading) {
        when (event) {
            CommunityScreenEvent.OpenScreen -> {
                showScreen()
            }
        }
    }

    override fun obtain(event: CommunityScreenEvent) {
        when (event) {
            is CommunityScreenEvent.OpenScreen -> reduce(
                event = CommunityScreenEvent.OpenScreen,
                state = CommunityScreenViewState.Loading
            )
        }
    }

    override fun viewState(): MutableStateFlow<CommunityScreenViewState> {
        return _viewState
    }

    private fun showScreen() {
        viewModelScope.launch {
            val communityListFlow = getCommunitiesUseCase.execute()

            _viewState.value = (
                    CommunityScreenViewState.Display(communities = communityListFlow)
                    )
        }

    }
}