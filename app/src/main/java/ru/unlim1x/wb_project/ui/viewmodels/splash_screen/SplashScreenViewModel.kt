package ru.unlim1x.wb_project.ui.viewmodels.splash_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class SplashScreenViewModel():MainViewModel<SplashScreenEvent, SplashScreenViewState>() {
    private val _viewState: MutableLiveData<SplashScreenViewState> =
        MutableLiveData(SplashScreenViewState.Init)

    init{
        Log.e("Created", "CREATED VM")
    }
    override fun obtain(event: SplashScreenEvent) {
        Log.e("SSVM", "Obtained")
        when(event){

            SplashScreenEvent.OpenApp->{
                reduce(event, SplashScreenViewState.Init)
            }
        }
    }

    private fun reduce(event: SplashScreenEvent, state: SplashScreenViewState.Init){
        Log.e("SSVM", "Reduces")
        showScreen()
    }
    private fun showScreen(){
        Log.e("SSVM", "Showscreen")
        viewModelScope.launch(Dispatchers.Default) {
            _viewState.postValue(SplashScreenViewState.Display)
            Log.e("SSVM", "_viewstate= ${_viewState.value}")
            //Грузим что-то тяжелое
            //Проверяем авторизацию
            val authorized = false
            delay(3000)
            _viewState.postValue(SplashScreenViewState.Finished(isAuthorized = authorized))


        }
    }

    override fun viewState(): LiveData<SplashScreenViewState> {
        return _viewState
    }
}