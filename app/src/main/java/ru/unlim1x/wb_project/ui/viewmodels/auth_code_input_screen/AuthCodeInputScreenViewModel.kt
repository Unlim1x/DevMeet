package ru.unlim1x.wb_project.ui.viewmodels.auth_code_input_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.ISaveNumberUseCase
import ru.lim1x.domain.interfaces.usecases.IValidateCodeUseCase
import ru.unlim1x.wb_project.ui.uiKit.custominputview.model.Country
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class AuthCodeInputScreenViewModel(private val validateCodeUseCase:IValidateCodeUseCase,
    private val savePhoneUseCase:ISaveNumberUseCase):MainViewModel<AuthCodeInputScreenEvent, AuthCodeInputScreenViewState>() {


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
                viewModelScope.launch {
                    if(validateCodeUseCase.execute(event.code).isSuccessful) {
                        savePhoneUseCase.execute(event.phone)
                        _viewState.postValue(AuthCodeInputScreenViewState.Valid)
                    }
                    //todo: обработать неверный код,
                    //todo: обработать сохранение id пользователя
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