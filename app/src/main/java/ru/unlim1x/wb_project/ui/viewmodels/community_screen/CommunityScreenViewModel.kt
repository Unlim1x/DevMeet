package ru.unlim1x.wb_project.ui.viewmodels.community_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Community
import ru.unlim1x.wb_project.ui.uiKit.cards.model.LoremIpsum
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel


class CommunityScreenViewModel():MainViewModel<CommunityScreenEvent, CommunityScreenViewState>() {

    private val _viewState: MutableLiveData<CommunityScreenViewState> =
        MutableLiveData(CommunityScreenViewState.Loading)

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

    override fun viewState(): LiveData<CommunityScreenViewState> {
        return this._viewState
    }

    private fun showScreen(){
        viewModelScope.launch {
            val communityList: List<Community> = List(20) { id->
                Community(
                    "Designa",
                    quantityMembers = 10000,
                    id = id,
                    description = LoremIpsum.Short.text
                )
            }

            _viewState.postValue(
                CommunityScreenViewState.Display(communities = flow { emit(communityList) })
            )
        }

    }
}