package ru.unlim1x.wb_project.ui.viewmodels.more_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.last
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

    private val _viewState: MutableLiveData<MoreScreenViewState> =
        MutableLiveData(MoreScreenViewState.Init)

    override fun obtain(event: MoreScreenEvent) {
        when (event) {
            MoreScreenEvent.OpenScreen -> {
                reduce(event, MoreScreenViewState.Init)
            }
        }
    }

    private fun reduce(event: MoreScreenEvent, state: MoreScreenViewState.Init) {
        showScreen()
    }

    override fun viewState(): LiveData<MoreScreenViewState> {
        return _viewState
    }

    private fun showScreen() {
        viewModelScope.launch {
            val user = getUserProfileDataUseCase.execute(getCurrentUserUseCase.execute())

            val listOfContainers = Containers.listOfContainers


            _viewState.postValue(
                MoreScreenViewState.Display(
                    user = user.last(),
                    containerList = listOfContainers
                )
            )
        }
    }
}