package ru.unlim1x.wb_project.ui.viewmodels.community_detailed_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetCommunityDetailedInfoByIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetMeetingsByCommunityIdUseCase
import ru.lim1x.domain.models.TimeAndPlace
import ru.lim1x.domain.models.Community
import ru.lim1x.domain.models.CommunityDetailed
import ru.lim1x.domain.models.LoremIpsum
import ru.lim1x.domain.models.Meeting
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class CommunityDetailedScreenViewModel(
    private val communityDetailedInfoByIdUseCase: IGetCommunityDetailedInfoByIdUseCase,
    private val meetingsByCommunityIdUseCase: IGetMeetingsByCommunityIdUseCase
):MainViewModel<CommunityDetailedScreenEvent, CommunityDetailedScreenViewState>() {
    private val _viewState: MutableLiveData<CommunityDetailedScreenViewState> =
        MutableLiveData(CommunityDetailedScreenViewState.Init)

    private lateinit var communityInitial: CommunityDetailed

    override fun obtain(event: CommunityDetailedScreenEvent) {
        when(event){
            is CommunityDetailedScreenEvent.OpenScreen -> reduce(event, CommunityDetailedScreenViewState.Init)

        }
    }

    private fun reduce(event: CommunityDetailedScreenEvent, state: CommunityDetailedScreenViewState.Init){
        when(event){
            is CommunityDetailedScreenEvent.OpenScreen -> {
                loadData(event.id)
            }
        }
    }


    override fun viewState(): LiveData<CommunityDetailedScreenViewState> {
        return _viewState
    }

    private fun loadData(id:Int){
        viewModelScope.launch {
           val community = communityDetailedInfoByIdUseCase.execute(id)
            val meetingsList = meetingsByCommunityIdUseCase.execute(id)
            communityInitial = community.first()
        _viewState.postValue(CommunityDetailedScreenViewState.Display(community = community, listOfMeetings = meetingsList, initial = communityInitial))
        }
    }

}