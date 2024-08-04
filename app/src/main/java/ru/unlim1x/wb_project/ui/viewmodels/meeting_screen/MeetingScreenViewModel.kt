package ru.unlim1x.wb_project.ui.viewmodels.meeting_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetActiveMeetingsUseCase
import ru.lim1x.domain.interfaces.usecases.IGetAllMeetingsUseCase
import ru.lim1x.domain.models.TimeAndPlace
import ru.lim1x.domain.models.Meeting
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class MeetingScreenViewModel(
    getAllMeetingsUseCase: IGetAllMeetingsUseCase,
    getActiveMeetingsUseCase: IGetActiveMeetingsUseCase
) : MainViewModel<MeetingScreenEvent, MeetingScreenViewState>() {

    private val _viewState: MutableStateFlow<MeetingScreenViewState> =
        MutableStateFlow(MeetingScreenViewState.Loading)

    private val _meetingsAllFlow: MutableStateFlow<List<Meeting>> = MutableStateFlow(emptyList())
    private val _meetingsActiveFlow: MutableStateFlow<List<Meeting>> = MutableStateFlow(emptyList())

    init{

        getAllMeetingsUseCase.execute().zip(getActiveMeetingsUseCase.execute()){listAll, listActive->
           _meetingsAllFlow.update { listAll }
            _meetingsActiveFlow.update { listActive }
            _viewState.update {
                MeetingScreenViewState.Display(
                    allMeetings = _meetingsAllFlow,
                    activeMeetings = _meetingsActiveFlow
                )
            }
        }.launchIn(viewModelScope)

    }
    private fun reduce(event: MeetingScreenEvent, state: MeetingScreenViewState.Loading) {
        when (event) {
            is MeetingScreenEvent.OpenScreen -> {
            }
        }
    }

    override fun obtain(event: MeetingScreenEvent) {
        when (event) {
            is MeetingScreenEvent.OpenScreen -> reduce(
                event = MeetingScreenEvent.OpenScreen,
                state = MeetingScreenViewState.Loading
            )
        }
    }

    override fun viewState(): MutableStateFlow<MeetingScreenViewState> {
        return _viewState
    }


}
