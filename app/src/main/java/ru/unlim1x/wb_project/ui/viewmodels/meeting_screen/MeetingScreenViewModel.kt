package ru.unlim1x.wb_project.ui.viewmodels.meeting_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Meeting
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class MeetingScreenViewModel() : MainViewModel<MeetingScreenEvent, MeetingScreenViewState>() {

    private val _viewState: MutableLiveData<MeetingScreenViewState> =
        MutableLiveData(MeetingScreenViewState.Loading)

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
            val listOfTags = listOf("Junior", "Python", "Moscow")
            val listMeetingsAll: MutableList<Meeting> = MutableList(15) {
                Meeting(
                    name = "Developer meeting",
                    timeAndPlace = TimeAndPlace(
                        place = "Moscow",
                        date = 13,
                        month = 9,
                        year = 2024
                    ),
                    isFinished = it % 4 == 0,
                    tags = listOfTags
                )
            }
            val listMeetingsActive: MutableList<Meeting> = MutableList(3) {
                Meeting(
                    name = "Developer meeting",
                    timeAndPlace = TimeAndPlace(
                        place = "Moscow",
                        date = 13,
                        month = 9,
                        year = 2024
                    ),
                    isFinished = it % 4 == 0,
                    tags = listOfTags
                )
            }

            _viewState.postValue(
                MeetingScreenViewState.Display(
                    allMeetings = flow{emit(listMeetingsAll)},
                    activeMeetings = flow{emit(listMeetingsActive)}
                )
            )
        }

    }
}
