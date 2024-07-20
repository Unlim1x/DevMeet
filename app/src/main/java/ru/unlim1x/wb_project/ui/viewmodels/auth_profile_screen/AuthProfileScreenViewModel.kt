package ru.unlim1x.wb_project.ui.viewmodels.auth_profile_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.uiKit.custominputview.model.Country
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class AuthProfileScreenViewModel():MainViewModel<AuthProfileScreenEvent, AuthProfileScreenViewState>() {

    private var name = ""
    private var surname = ""

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
                name = event.name
                surname = event.surname
            }
        }
        //TODO: юзкейс регистрации пользователя
        viewModelScope.launch {
            _viewState.postValue(AuthProfileScreenViewState.Saved)
        }
    }


    override fun viewState(): LiveData<AuthProfileScreenViewState> {
       return _viewState
    }
}