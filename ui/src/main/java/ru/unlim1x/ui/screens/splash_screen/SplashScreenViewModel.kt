package ru.unlim1x.ui.screens.splash_screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.screens.MainViewModel

internal class SplashScreenViewModel : MainViewModel<SplashScreenEvent, SplashScreenViewState>() {
    override val _viewState: MutableStateFlow<SplashScreenViewState> =
        MutableStateFlow(SplashScreenViewState.Init)

    init {
        Log.e("Created", "CREATED VM")
    }

    override fun obtain(event: SplashScreenEvent) {
        Log.e("SSVM", "Obtained")
        when (event) {

            SplashScreenEvent.OpenApp -> {
                reduce(event, SplashScreenViewState.Init)
            }
        }
    }

    private fun reduce(event: SplashScreenEvent, state: SplashScreenViewState.Init) {
        Log.e("SSVM", "Reduces")
        showScreen()
    }

    private fun showScreen() {
        Log.e("SSVM", "Showscreen")
        viewModelScope.launch(Dispatchers.Default) {
            _viewState.value = (SplashScreenViewState.Display)
            Log.e("SSVM", "_viewstate= ${_viewState.value}")
            //Грузим что-то тяжелое.
            //Проверяем авторизацию
            val authorized = false
            delay(3000)
            _viewState.value = (SplashScreenViewState.Finished(isAuthorized = authorized))


        }
    }

    override fun viewState(): MutableStateFlow<SplashScreenViewState> {
        return _viewState
    }
}