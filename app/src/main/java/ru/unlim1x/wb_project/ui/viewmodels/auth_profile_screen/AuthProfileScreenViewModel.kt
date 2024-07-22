package ru.unlim1x.wb_project.ui.viewmodels.auth_profile_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetCurrentUserIdUseCase
import ru.lim1x.domain.interfaces.usecases.ISaveUserProfileNameUseCase
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class AuthProfileScreenViewModel(
    private val saveUserNameUseCase:ISaveUserProfileNameUseCase,
    private val getCurrentUserUseCase: IGetCurrentUserIdUseCase
    ):MainViewModel<AuthProfileScreenEvent, AuthProfileScreenViewState>() {


    private val _viewState: MutableLiveData<AuthProfileScreenViewState> =
        MutableLiveData(AuthProfileScreenViewState.Display)
    override fun obtain(event: AuthProfileScreenEvent) {
        when(event){

            is AuthProfileScreenEvent.Save->{
                reduce(event, AuthProfileScreenViewState.Display)
            }
        }
    }

    fun reduce(event: AuthProfileScreenEvent, state: AuthProfileScreenViewState.Display){
        when (event){
            is AuthProfileScreenEvent.Save->{

                viewModelScope.launch {
                    val userId = getCurrentUserUseCase.execute()
                    saveUserNameUseCase.execute(userId = userId, event.name, event.surname)
                    _viewState.postValue(AuthProfileScreenViewState.Saved)
                }
            }
        }

    }


    override fun viewState(): LiveData<AuthProfileScreenViewState> {
       return _viewState
    }
}