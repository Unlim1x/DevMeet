package ru.unlim1x.wb_project.ui.viewmodels.profile_screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetCurrentUserIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetUserProfileDataUseCase
import ru.lim1x.domain.interfaces.usecases.ISetUserPhotoExperimentalUseCase
import ru.lim1x.domain.usecase_implementation.profile.SetUserPhotoExperimentalUseCase
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel


class ProfileScreenViewModel(
    getCurrentUserUseCase: IGetCurrentUserIdUseCase,
    getUserProfileDataUseCase: IGetUserProfileDataUseCase,
    val setUserPhotoExperimentalUseCase: ISetUserPhotoExperimentalUseCase
) : MainViewModel<ProfileScreenEvent, ProfileScreenViewState>() {

    override val _viewState: MutableStateFlow<ProfileScreenViewState> =
        MutableStateFlow(ProfileScreenViewState.Init)

    init {
        getUserProfileDataUseCase.execute(getCurrentUserUseCase.execute()).onEach {
            _viewState.value = (ProfileScreenViewState.Display(user = it))
        }.launchIn(viewModelScope)
    }

    private fun reduce(event: ProfileScreenEvent, state: ProfileScreenViewState.Init) {
        when (event) {
            ProfileScreenEvent.OpenScreen -> {}
            is ProfileScreenEvent.UserChoseImage -> TODO()
        }
    }

    override fun obtain(event: ProfileScreenEvent) {
        when (event) {
            ProfileScreenEvent.OpenScreen -> {
                reduce(event, ProfileScreenViewState.Init)
            }

            is ProfileScreenEvent.UserChoseImage -> {
                viewModelScope.launch {
                    Log.e("", "ПРОБРАСЫВАЕПТСЯ ВО ВЬЮ МОДЕЛЬ!!!!!")
                    Log.e("URI.encoded", "${event.uri.encodedPath}")
                    Log.e("URI", "${event.uri}")
                    //todo: посмотреть как парсить путь
                    setUserPhotoExperimentalUseCase.execute(event.uri.toString())
                }
            }
        }
    }

    override fun viewState(): MutableStateFlow<ProfileScreenViewState> {
        return _viewState
    }


}