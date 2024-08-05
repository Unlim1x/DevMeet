package ru.unlim1x.wb_project.ui.viewmodels.auth_code_input_screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.ISaveNumberUseCase
import ru.lim1x.domain.interfaces.usecases.IValidateCodeUseCase
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class AuthCodeInputScreenViewModel(
    private val validateCodeUseCase: IValidateCodeUseCase,
    private val savePhoneUseCase: ISaveNumberUseCase
) : MainViewModel<AuthCodeInputScreenEvent, AuthCodeInputScreenViewState>() {


    override val _viewState: MutableStateFlow<AuthCodeInputScreenViewState> =
        MutableStateFlow(AuthCodeInputScreenViewState.Display)

    override fun obtain(event: AuthCodeInputScreenEvent) {
        when (event) {
            is AuthCodeInputScreenEvent.Validate -> {
                reduce(event, AuthCodeInputScreenViewState.Display)
            }

            is AuthCodeInputScreenEvent.Resend -> {
                reduce(event, AuthCodeInputScreenViewState.Display)
            }
        }
    }

    private fun reduce(
        event: AuthCodeInputScreenEvent,
        state: AuthCodeInputScreenViewState.Display
    ) {
        when (event) {
            is AuthCodeInputScreenEvent.Validate -> {
                viewModelScope.launch {
                    if (validateCodeUseCase.execute(event.code).value?.isSuccessful == true) {
                        savePhoneUseCase.execute(event.phone)
                        _viewState.value = (AuthCodeInputScreenViewState.Valid)
                    }
                    //todo: обработать неверный код,
                    //todo: обработать сохранение id пользователя
                }
            }

            AuthCodeInputScreenEvent.Resend -> {
                //TODO: юзкейс повторной отправки кода
            }
        }
    }


    override fun viewState(): StateFlow<AuthCodeInputScreenViewState> {
        return _viewState
    }
}