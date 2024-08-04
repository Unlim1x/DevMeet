package ru.unlim1x.wb_project.ui.viewmodels.community_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetCommunitiesUseCase
import ru.lim1x.domain.models.Community
import ru.lim1x.domain.models.LoremIpsum
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel


class CommunityScreenViewModel(
    private val getCommunitiesUseCase: IGetCommunitiesUseCase
):MainViewModel<CommunityScreenEvent, CommunityScreenViewState>() {

    private val _viewState: MutableStateFlow<CommunityScreenViewState> =
        MutableStateFlow(CommunityScreenViewState.Loading)

    private fun reduce(event:CommunityScreenEvent, state: CommunityScreenViewState.Loading){
        when (event){
            CommunityScreenEvent.OpenScreen-> {showScreen()}
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

    private fun showScreen(){
        viewModelScope.launch {
            val communityListFlow = getCommunitiesUseCase.execute()

            _viewState.value = (
                CommunityScreenViewState.Display(communities = communityListFlow)
            )
        }

    }
}