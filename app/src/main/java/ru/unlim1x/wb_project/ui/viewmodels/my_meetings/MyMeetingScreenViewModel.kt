package ru.unlim1x.wb_project.ui.viewmodels.my_meetings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetCurrentUserIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetFinishedMeetingsUseCase
import ru.lim1x.domain.interfaces.usecases.IGetPlannedMeetingsUseCase
import ru.lim1x.domain.models.TimeAndPlace
import ru.lim1x.domain.models.Meeting
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class MyMeetingScreenViewModel(
    getPlannedMeetingsUseCase: IGetPlannedMeetingsUseCase,
    getFinishedMeetingsUseCase: IGetFinishedMeetingsUseCase,
    getCurrentUserIdUseCase: IGetCurrentUserIdUseCase
) : MainViewModel<MyMeetingScreenEvent, MyMeetingScreenViewState>() {

    private val _viewState: MutableStateFlow<MyMeetingScreenViewState> =
        MutableStateFlow(MyMeetingScreenViewState.Loading)

    private val _plannedMeetingsFlow: MutableStateFlow<List<Meeting>> = MutableStateFlow(emptyList())
    private val _finishedMeetingsFlow: MutableStateFlow<List<Meeting>> = MutableStateFlow(emptyList())

    init{

        getPlannedMeetingsUseCase.execute(getCurrentUserIdUseCase.execute()).zip(getFinishedMeetingsUseCase.execute()){
            plannedList, finishedList->
            _plannedMeetingsFlow.update { plannedList }
            _finishedMeetingsFlow.update { finishedList }
            _viewState.update { MyMeetingScreenViewState.Display(
                _plannedMeetingsFlow,
                _finishedMeetingsFlow
            ) }
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
