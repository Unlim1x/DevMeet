package ru.unlim1x.ui.screens.meeting_screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import ru.lim1x.domain.interfaces.usecases.IGetActiveMeetingsUseCase
import ru.lim1x.domain.interfaces.usecases.IGetAllMeetingsUseCase
import ru.lim1x.domain.models.Meeting
import ru.unlim1x.wb_project.ui.screens.MainViewModel

internal class MeetingScreenViewModel(
    getAllMeetingsUseCase: IGetAllMeetingsUseCase,
    getActiveMeetingsUseCase: IGetActiveMeetingsUseCase
) : MainViewModel<MeetingScreenEvent, MeetingScreenViewState>() {

    override val _viewState: MutableStateFlow<MeetingScreenViewState> =
        MutableStateFlow(MeetingScreenViewState.Loading)

    private val _meetingsAllFlow: MutableStateFlow<List<Meeting>> = MutableStateFlow(emptyList())
    private val _meetingsActiveFlow: MutableStateFlow<List<Meeting>> = MutableStateFlow(emptyList())

    init {

        getAllMeetingsUseCase.execute()
            .combine(getActiveMeetingsUseCase.execute()) { listAll, listActive ->
                Pair(listAll,listActive)
            }.onEach { pair->
                _meetingsAllFlow.update { pair.first }
                _meetingsActiveFlow.update { pair.second }
                delay(4000)
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
