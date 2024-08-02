package ru.unlim1x.wb_project.ui.viewmodels.my_meetings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetFinishedMeetingsUseCase
import ru.lim1x.domain.interfaces.usecases.IGetPlannedMeetingsUseCase
import ru.lim1x.domain.models.TimeAndPlace
import ru.lim1x.domain.models.Meeting
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class MyMeetingScreenViewModel(
    private val getPlannedMeetingsUseCase: IGetPlannedMeetingsUseCase,
    private val getFinishedMeetingsUseCase: IGetFinishedMeetingsUseCase
) : MainViewModel<MyMeetingScreenEvent, MyMeetingScreenViewState>() {

    private val _viewState: MutableLiveData<MyMeetingScreenViewState> =
        MutableLiveData(MyMeetingScreenViewState.Loading)

    private lateinit var plannedMeetingsFlow: Flow<List<Meeting>>
    private lateinit var finishedMeetingsFlow: Flow<List<Meeting>>

    private fun reduce(event: MyMeetingScreenEvent, state: MyMeetingScreenViewState.Loading) {
        when (event) {
            is MyMeetingScreenEvent.OpenScreen -> {
                showScreen()
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

    override fun viewState(): LiveData<MyMeetingScreenViewState> {
        return this._viewState
    }

    fun showScreen() {
        viewModelScope.launch {
            //todo: потом передать параметр id встречи, сейчас моки возвращают конкретные списки
            plannedMeetingsFlow = getPlannedMeetingsUseCase.execute(0)
            finishedMeetingsFlow = getFinishedMeetingsUseCase.execute()

            _viewState.postValue(
                MyMeetingScreenViewState.Display(
                    plannedMeetings = plannedMeetingsFlow,
                    finishedMeetings = finishedMeetingsFlow
                )
            )
        }

    }
}
