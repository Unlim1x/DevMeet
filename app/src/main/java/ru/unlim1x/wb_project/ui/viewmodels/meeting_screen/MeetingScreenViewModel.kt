package ru.unlim1x.wb_project.ui.viewmodels.meeting_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetActiveMeetingsUseCase
import ru.lim1x.domain.interfaces.usecases.IGetAllMeetingsUseCase
import ru.lim1x.domain.models.TimeAndPlace
import ru.lim1x.domain.models.Meeting
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class MeetingScreenViewModel(
    private val getAllMeetingsUseCase: IGetAllMeetingsUseCase,
    private val getActiveMeetingsUseCase: IGetActiveMeetingsUseCase
) : MainViewModel<MeetingScreenEvent, MeetingScreenViewState>() {

    private val _viewState: MutableLiveData<MeetingScreenViewState> =
        MutableLiveData(MeetingScreenViewState.Loading)

    private lateinit var meetingsAllFlow: Flow<List<Meeting>>
    private lateinit var meetingsActiveFlow: Flow<List<Meeting>>

    private fun reduce(event: MeetingScreenEvent, state: MeetingScreenViewState.Loading) {
        when (event) {
            is MeetingScreenEvent.OpenScreen -> {
                showMeetings()
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

    override fun viewState(): LiveData<MeetingScreenViewState> {
        return this._viewState
    }

    private fun showMeetings() {
        viewModelScope.launch {
            meetingsAllFlow = getAllMeetingsUseCase.execute()
            meetingsActiveFlow = getActiveMeetingsUseCase.execute()

            _viewState.postValue(
                MeetingScreenViewState.Display(
                    allMeetings = meetingsAllFlow,
                    activeMeetings = meetingsActiveFlow
                )
            )
        }

    }
}
