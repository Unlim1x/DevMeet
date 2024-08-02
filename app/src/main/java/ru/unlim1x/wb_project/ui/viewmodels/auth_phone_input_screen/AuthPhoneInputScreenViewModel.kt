package ru.unlim1x.wb_project.ui.viewmodels.auth_phone_input_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.ISendCodeToPhoneUseCase
import ru.unlim1x.wb_project.ui.uiKit.custominputview.model.Country
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class AuthPhoneInputScreenViewModel(private val sendCodeUseCase:ISendCodeToPhoneUseCase):MainViewModel<AuthPhoneInputScreenEvent, AuthPhoneInputScreenViewState>() {

    init {
        Log.e("AUTH", "AUTH VIEW MODEL CREATED")
    }

    private var countryCode: String = Country.Russia.phoneCode
    private var phoneNumber = ""

    private val _viewState: MutableLiveData<AuthPhoneInputScreenViewState> =
        MutableLiveData(AuthPhoneInputScreenViewState.Display)
    override fun obtain(event: AuthPhoneInputScreenEvent) {
        when(event){

            is AuthPhoneInputScreenEvent.SendCode->{
                reduce(event, AuthPhoneInputScreenViewState.Display)
            }
        }
    }

    fun reduce(event: AuthPhoneInputScreenEvent, state: AuthPhoneInputScreenViewState.Display){
        when (event){
            is AuthPhoneInputScreenEvent.SendCode->{
                countryCode = event.countryCode
                phoneNumber = event.phoneNumber

                viewModelScope.launch {
                    sendCodeUseCase.execute(phoneNumber)
                    _viewState.postValue(AuthPhoneInputScreenViewState.Sent(countryCode, phoneNumber))
                }
            }
        }

    }


    override fun viewState(): LiveData<AuthPhoneInputScreenViewState> {
       return _viewState
    }
}