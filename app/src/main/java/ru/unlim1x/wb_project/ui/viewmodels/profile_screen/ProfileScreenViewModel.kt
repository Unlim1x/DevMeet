package ru.unlim1x.wb_project.ui.viewmodels.profile_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.screens.model.User
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel
import ru.unlim1x.wb_project.ui.viewmodels.community_screen.CommunityScreenEvent
import ru.unlim1x.wb_project.ui.viewmodels.community_screen.CommunityScreenViewState


class ProfileScreenViewModel():MainViewModel<ProfileScreenEvent, ProfileScreenViewState>() {

    private val _viewState: MutableLiveData<ProfileScreenViewState> =
        MutableLiveData(ProfileScreenViewState.Init)

    private fun reduce(event: ProfileScreenEvent, state: ProfileScreenViewState.Init){
        when (event){
            ProfileScreenEvent.OpenScreen-> {showScreen()}
        }
    }
    override fun obtain(event: ProfileScreenEvent) {
        when(event){
            ProfileScreenEvent.OpenScreen->{reduce(event, ProfileScreenViewState.Init)}
        }
    }

    override fun viewState(): LiveData<ProfileScreenViewState> {
        return this._viewState
    }

    private fun showScreen(){
        viewModelScope.launch {
            _viewState.postValue(ProfileScreenViewState.Display(user =
            User(
                    name = "Иван Иванов",
                    phone = "+7 999 999-99-99",
                    avatarURL = "",
                    hasAvatar = false
                ))
            )
        }
    }
}