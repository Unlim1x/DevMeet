package ru.unlim1x.old_ui.screens.community_screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import ru.lim1x.domain.interfaces.usecases.IGetCommunitiesUseCase
import ru.lim1x.domain.models.Community
import ru.unlim1x.old_ui.screens.MainViewModel


internal class CommunityScreenViewModel(
    private val getCommunitiesUseCase: IGetCommunitiesUseCase
) : MainViewModel<CommunityScreenEvent, CommunityScreenViewState>() {
    private val _communitiesStateFlow: MutableStateFlow<List<Community>> = MutableStateFlow(emptyList())
    override val _viewState: MutableStateFlow<CommunityScreenViewState> =
        MutableStateFlow(CommunityScreenViewState.Loading)

    init {
        getCommunitiesUseCase.execute().onEach {
            Log.e("", "$it")
            //todo: заменить на update -> найти, где ошибка во flow
            _communitiesStateFlow.value=  it
            delay(4000)
            _viewState.update {
                CommunityScreenViewState.Display(communities = _communitiesStateFlow)
            }
        }.launchIn(viewModelScope)

    }

    private fun reduce(event: CommunityScreenEvent, state: CommunityScreenViewState.Loading) {
        when (event) {
            CommunityScreenEvent.OpenScreen -> {
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


}