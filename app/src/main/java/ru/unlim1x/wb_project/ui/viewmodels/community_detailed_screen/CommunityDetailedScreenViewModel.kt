package ru.unlim1x.wb_project.ui.viewmodels.community_detailed_screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetCommunityDetailedInfoByIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetMeetingsByCommunityIdUseCase
import ru.lim1x.domain.models.CommunityDetailed
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class CommunityDetailedScreenViewModel(
    private val communityDetailedInfoByIdUseCase: IGetCommunityDetailedInfoByIdUseCase,
    private val meetingsByCommunityIdUseCase: IGetMeetingsByCommunityIdUseCase
) : MainViewModel<CommunityDetailedScreenEvent, CommunityDetailedScreenViewState>() {
    private val _viewState: MutableStateFlow<CommunityDetailedScreenViewState> =
        MutableStateFlow(CommunityDetailedScreenViewState.Init)

    private lateinit var communityInitial: CommunityDetailed

    override fun obtain(event: CommunityDetailedScreenEvent) {
        when (event) {
            is CommunityDetailedScreenEvent.OpenScreen -> reduce(
                event,
                CommunityDetailedScreenViewState.Init
            )

        }
    }

    private fun reduce(
        event: CommunityDetailedScreenEvent,
        state: CommunityDetailedScreenViewState.Init
    ) {
        when (event) {
            is CommunityDetailedScreenEvent.OpenScreen -> {
                loadData(event.id)
            }
        }
    }


    override fun viewState(): MutableStateFlow<CommunityDetailedScreenViewState> {
        return _viewState
    }

    private fun loadData(id: Int) {
        viewModelScope.launch {
            val community = communityDetailedInfoByIdUseCase.execute(id)
            val meetingsList = meetingsByCommunityIdUseCase.execute(id)
            communityInitial = community.first()!!
            _viewState.value = (CommunityDetailedScreenViewState.Display(
                community = community.filterNotNull(),
                listOfMeetings = meetingsList,
                initial = communityInitial
            ))
        }
    }

}