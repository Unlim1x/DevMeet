package ru.unlim1x.wb_project.ui.viewmodels.more_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.usecases.IGetCurrentUserIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetUserProfileDataUseCase
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.screens.model.MoreContainerData
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class MoreScreenViewModel(
    private val getUserProfileDataUseCase: IGetUserProfileDataUseCase,
    private val getCurrentUserUseCase: IGetCurrentUserIdUseCase
) : MainViewModel<MoreScreenEvent, MoreScreenViewState>() {

    private val _viewState: MutableStateFlow<MoreScreenViewState> =
        MutableStateFlow(MoreScreenViewState.Init)

    init{
        val listOfContainers = Containers.listOfContainers
        getUserProfileDataUseCase.execute(getCurrentUserUseCase.execute()).onEach{
            _viewState.value=
                (
                        MoreScreenViewState.Display(
                            user = it,
                            containerList = listOfContainers
                        )
                        )
        }.launchIn(viewModelScope)
    }

    override fun obtain(event: MoreScreenEvent) {
        when (event) {
            MoreScreenEvent.OpenScreen -> {
                reduce(event, MoreScreenViewState.Init)
            }
        }
    }

    private fun reduce(event: MoreScreenEvent, state: MoreScreenViewState.Init) {

    }

    override fun viewState(): StateFlow<MoreScreenViewState> {
        return _viewState.asStateFlow()
    }


}