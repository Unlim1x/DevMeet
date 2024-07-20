package ru.unlim1x.wb_project.ui.viewmodels.community_detailed_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Community
import ru.unlim1x.wb_project.ui.uiKit.cards.model.LoremIpsum
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Meeting
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel
import ru.unlim1x.wb_project.ui.viewmodels.profile_screen.ProfileScreenViewState

class CommunityDetailedScreenViewModel():MainViewModel<CommunityDetailedScreenEvent, CommunityDetailedScreenViewState>() {
    private val _viewState: MutableLiveData<CommunityDetailedScreenViewState> =
        MutableLiveData(CommunityDetailedScreenViewState.Init)

    //private val emptyCommunity = Community("", TimeAndPlace("",1,2,3), true, emptyList())
    private val emptyCommunity = Community("","",0,0,"")
    private var id = 0 //todo:убрать
    override fun obtain(event: CommunityDetailedScreenEvent) {
        when(event){
            CommunityDetailedScreenEvent.OpenScreen -> reduce(event, CommunityDetailedScreenViewState.Init)

        }
    }

    private fun reduce(event: CommunityDetailedScreenEvent, state: CommunityDetailedScreenViewState.Init){
        loadData()
    }


    override fun viewState(): LiveData<CommunityDetailedScreenViewState> {
        return _viewState
    }

    private fun loadData(){
        viewModelScope.launch {
            val community = Community(
                name = "Designa",
                id = id++,
                quantityMembers = 10000,
                description = LoremIpsum.Short.text
            )
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

        _viewState.postValue(CommunityDetailedScreenViewState.Display(community = community, listOfMeetings = listMeetingsAll))
        }
    }

}