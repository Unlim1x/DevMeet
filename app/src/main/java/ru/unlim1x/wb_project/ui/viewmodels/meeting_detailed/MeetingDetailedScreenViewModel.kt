package ru.unlim1x.wb_project.ui.viewmodels.meeting_detailed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Meeting
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel
import ru.unlim1x.wb_project.ui.viewmodels.profile_screen.ProfileScreenViewState

class MeetingDetailedScreenViewModel():MainViewModel<MeetingDetailedScreenEvent, MeetingDetailedScreenViewState>() {
    private val _viewState: MutableLiveData<MeetingDetailedScreenViewState> =
        MutableLiveData(MeetingDetailedScreenViewState.Init)

    private val emptyMeeting = Meeting("", TimeAndPlace("",1,2,3), true, emptyList())
    override fun obtain(event: MeetingDetailedScreenEvent) {
        when(event){
            MeetingDetailedScreenEvent.OpenScreen -> reduce(event, MeetingDetailedScreenViewState.Init)
            MeetingDetailedScreenEvent.WillGo -> reduce(event, MeetingDetailedScreenViewState.DisplayNotGo(
                emptyMeeting,
                emptyList(),
                false
            ))

            MeetingDetailedScreenEvent.WillNotGo -> reduce(event, MeetingDetailedScreenViewState.DisplayGo(
                emptyMeeting,
                emptyList(),
                true
            ))
        }
    }

    private fun reduce(event: MeetingDetailedScreenEvent, state: MeetingDetailedScreenViewState.Init){
        //todo: загрузить и узнать идет ли пользователь
        fetchNotGo()
    }
    private fun reduce(event: MeetingDetailedScreenEvent, state: MeetingDetailedScreenViewState.DisplayGo){
        //TODO: Обработать " не пойду"
        when(event){
            MeetingDetailedScreenEvent.WillNotGo -> {
                fetchNotGo()
            }
            else->throw NotImplementedError("Unexpected state")
        }
    }

    private fun reduce(event: MeetingDetailedScreenEvent, state: MeetingDetailedScreenViewState.DisplayNotGo){
        //TODO: Обработать " пойду"
        when(event){
            MeetingDetailedScreenEvent.WillGo -> {
                fetchGo()
            }
            else->throw NotImplementedError("Unexpected state")
        }
    }

    override fun viewState(): LiveData<MeetingDetailedScreenViewState> {
        return _viewState
    }

    private fun fetchGo(){
        viewModelScope.launch {
            val listOfAvatars: MutableList<String> =
                MutableList(11) { "https://get.wallhere.com/photo/face-women-model-portrait-long-hair-photography-hair-nose-solo-Person-skin-head-supermodel-girl-beauty-eye-lip-blond-hairstyle-portrait-photography-photo-shoot-brown-hair-art-model-human-hair-color-hair-coloring-human-body-organ-close-up-layered-hair-5168.jpg" }
            val listOfTags = listOf("Junior", "Python", "Moscow")
            val meeting = Meeting(
                name = "Developer meeting",
                timeAndPlace = TimeAndPlace(
                    place = "Moscow",
                    date = 13,
                    month = 9,
                    year = 2024
                ),
                isFinished = false,
                tags = listOfTags
            )

        _viewState.postValue(MeetingDetailedScreenViewState.DisplayGo(meeting=meeting, comingAvatars = listOfAvatars, true))
        }
    }

    private fun fetchNotGo(){
        viewModelScope.launch {
            val listOfAvatars: MutableList<String> =
                MutableList(10) { "https://get.wallhere.com/photo/face-women-model-portrait-long-hair-photography-hair-nose-solo-Person-skin-head-supermodel-girl-beauty-eye-lip-blond-hairstyle-portrait-photography-photo-shoot-brown-hair-art-model-human-hair-color-hair-coloring-human-body-organ-close-up-layered-hair-5168.jpg" }
            val listOfTags = listOf("Junior", "Python", "Moscow")
            val meeting = Meeting(
                name = "Developer meeting",
                timeAndPlace = TimeAndPlace(
                    place = "Moscow",
                    date = 13,
                    month = 9,
                    year = 2024
                ),
                isFinished = false,
                tags = listOfTags
            )

            _viewState.postValue(MeetingDetailedScreenViewState.DisplayNotGo(meeting=meeting, comingAvatars = listOfAvatars, false))
        }
    }
}