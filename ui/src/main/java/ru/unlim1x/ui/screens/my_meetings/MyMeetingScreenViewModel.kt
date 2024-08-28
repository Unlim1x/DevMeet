package ru.unlim1x.ui.screens.my_meetings

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import ru.lim1x.domain.interfaces.usecases.IGetCurrentUserIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetFinishedMeetingsUseCase
import ru.lim1x.domain.interfaces.usecases.IGetPlannedMeetingsUseCase
import ru.lim1x.domain.models.Meeting
import ru.unlim1x.wb_project.ui.screens.MainViewModel

internal class MyMeetingScreenViewModel(
    getPlannedMeetingsUseCase: IGetPlannedMeetingsUseCase,
    getFinishedMeetingsUseCase: IGetFinishedMeetingsUseCase,
    getCurrentUserIdUseCase: IGetCurrentUserIdUseCase
) : MainViewModel<MyMeetingScreenEvent, MyMeetingScreenViewState>() {

    override val _viewState: MutableStateFlow<MyMeetingScreenViewState> =
        MutableStateFlow(MyMeetingScreenViewState.Loading)

    private val _plannedMeetingsFlow: MutableStateFlow<List<Meeting>> =
        MutableStateFlow(emptyList())
    private val _finishedMeetingsFlow: MutableStateFlow<List<Meeting>> =
        MutableStateFlow(emptyList())

    init {

        getPlannedMeetingsUseCase.execute(getCurrentUserIdUseCase.execute()).combine(getFinishedMeetingsUseCase.execute()){
            plannedList,finishedList->
            Pair(plannedList, finishedList)
        }.onEach{pair->
            _plannedMeetingsFlow.update { pair.first }
            _finishedMeetingsFlow.update { pair.second}
            _viewState.update { MyMeetingScreenViewState.Display(_plannedMeetingsFlow.asStateFlow(), _finishedMeetingsFlow.asStateFlow()) }
        }.launchIn(viewModelScope)

    }

    private fun reduce(event: MyMeetingScreenEvent, state: MyMeetingScreenViewState.Loading) {
        when (event) {
            is MyMeetingScreenEvent.OpenScreen -> {
            }
        }
    }

    override fun obtain(event: MyMeetingScreenEvent) {
        when (event) {
            is MyMeetingScreenEvent.OpenScreen -> reduce(
                event = MyMeetingScreenEvent.OpenScreen,
                state = MyMeetingScreenViewState.Loading
            )
        }
    }

    override fun viewState(): MutableStateFlow<MyMeetingScreenViewState> {
        return _viewState
    }

}
