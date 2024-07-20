package ru.unlim1x.wb_project.ui.viewmodels.my_meetings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Meeting
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class MyMeetingScreenViewModel() : MainViewModel<MyMeetingScreenEvent, MyMeetingScreenViewState>() {

    private val _viewState: MutableLiveData<MyMeetingScreenViewState> =
        MutableLiveData(MyMeetingScreenViewState.Loading)

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
            val listOfTags = listOf("Junior", "Python", "Moscow")
            val listMeetingsPlanned: MutableList<Meeting> = MutableList(15) {
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
            val listMeetingsFinished: MutableList<Meeting> = MutableList(3) {
                Meeting(
                    name = "Developer meeting",
                    timeAndPlace = TimeAndPlace(
                        place = "Moscow",
                        date = 13,
                        month = 9,
                        year = 2024
                    ),
                    isFinished = true,
                    tags = listOfTags
                )
            }

            _viewState.postValue(
                MyMeetingScreenViewState.Display(
                    plannedMeetings = flow{emit(listMeetingsPlanned)},
                    finishedMeetings = flow{emit(listMeetingsFinished)}
                )
            )
        }

    }
}
