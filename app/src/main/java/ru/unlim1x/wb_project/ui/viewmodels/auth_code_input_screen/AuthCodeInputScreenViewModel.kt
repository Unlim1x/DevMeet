package ru.unlim1x.wb_project.ui.viewmodels.auth_code_input_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.uiKit.custominputview.model.Country
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class AuthCodeInputScreenViewModel():MainViewModel<AuthCodeInputScreenEvent, AuthCodeInputScreenViewState>() {


    private val _viewState: MutableLiveData<AuthCodeInputScreenViewState> =
        MutableLiveData(AuthCodeInputScreenViewState.Display)

    override fun obtain(event: AuthCodeInputScreenEvent) {
        when(event){
            is AuthCodeInputScreenEvent.Validate->{
                reduce(event, AuthCodeInputScreenViewState.Display)
            }
            is AuthCodeInputScreenEvent.Resend->{
                reduce(event, AuthCodeInputScreenViewState.Display)
            }
        }
    }

    private fun reduce(event: AuthCodeInputScreenEvent, state: AuthCodeInputScreenViewState.Display){
        when(event){
            is AuthCodeInputScreenEvent.Validate->{
                //TODO: юзейкс проверки кода
                viewModelScope.launch {
                    _viewState.postValue(AuthCodeInputScreenViewState.Valid)
                }
            }
            AuthCodeInputScreenEvent.Resend->{
                //TODO: юзкейс повторной отправки кода
            }
        }
    }



    override fun viewState(): LiveData<AuthCodeInputScreenViewState> {
       return _viewState
    }
}