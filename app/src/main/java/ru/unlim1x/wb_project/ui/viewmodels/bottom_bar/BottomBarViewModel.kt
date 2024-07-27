package ru.unlim1x.wb_project.ui.viewmodels.bottom_bar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class BottomBarViewModel():MainViewModel<BottomBarEvent, BottomBarViewState>() {

    private val _viewState: MutableLiveData<BottomBarViewState> =
        MutableLiveData(BottomBarViewState.Init)
    override fun obtain(event: BottomBarEvent) {
        when(event){
            BottomBarEvent.LoadBottomBar->{
                reduce(event, BottomBarViewState.Init)
            }
        }
    }

    private fun reduce(event: BottomBarEvent, state: BottomBarViewState.Init){
        when(event){
            BottomBarEvent.LoadBottomBar->{
                showBar()
            }
        }
    }

    override fun viewState(): LiveData<BottomBarViewState> {
        return _viewState
    }

    private fun showBar(){
        viewModelScope.launch {
            val roots = listOf(
                NavGraphNodes.MeetingRoot,
                NavGraphNodes.CommunityRoot,
                NavGraphNodes.MoreRoot
            )
            _viewState.postValue(BottomBarViewState.Display(roots = roots, overengineering = 0))
        }
    }
}