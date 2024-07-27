package ru.unlim1x.wb_project.ui.viewmodels.profile_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetCurrentUserIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetUserProfileDataUseCase
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel


class ProfileScreenViewModel(
    private val getCurrentUserUseCase: IGetCurrentUserIdUseCase,
    private val getUserProfileDataUseCase: IGetUserProfileDataUseCase
):MainViewModel<ProfileScreenEvent, ProfileScreenViewState>() {

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
            val user = getUserProfileDataUseCase.execute(getCurrentUserUseCase.execute())
            _viewState.postValue(ProfileScreenViewState.Display(user =user.last()

            )
            )
        }
    }
}