package ru.unlim1x.wb_project.ui.viewmodels.profile_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetCurrentUserIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetUserProfileDataUseCase
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel


class ProfileScreenViewModel(
    getCurrentUserUseCase: IGetCurrentUserIdUseCase,
    getUserProfileDataUseCase: IGetUserProfileDataUseCase
):MainViewModel<ProfileScreenEvent, ProfileScreenViewState>() {

    private val _viewState: MutableStateFlow<ProfileScreenViewState> =
        MutableStateFlow(ProfileScreenViewState.Init)

    init {
        getUserProfileDataUseCase.execute(getCurrentUserUseCase.execute()).onEach {
            _viewState.value = (ProfileScreenViewState.Display(user =it))
        }.launchIn(viewModelScope)
    }

    private fun reduce(event: ProfileScreenEvent, state: ProfileScreenViewState.Init){
        when (event){
            ProfileScreenEvent.OpenScreen-> {}
        }
    }
    override fun obtain(event: ProfileScreenEvent) {
        when(event){
            ProfileScreenEvent.OpenScreen->{reduce(event, ProfileScreenViewState.Init)}
        }
    }

    override fun viewState(): MutableStateFlow<ProfileScreenViewState> {
        return _viewState
    }


}