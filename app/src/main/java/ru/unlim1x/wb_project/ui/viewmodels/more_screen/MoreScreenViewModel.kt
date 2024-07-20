package ru.unlim1x.wb_project.ui.viewmodels.more_screen

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.screens.model.MoreContainerData
import ru.unlim1x.wb_project.ui.screens.model.User
import ru.unlim1x.wb_project.ui.viewmodels.MainViewModel

class MoreScreenViewModel() : MainViewModel<MoreScreenEvent, MoreScreenViewState>() {

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
            val user = User(
                name = "Иван Иванов",
                phone = "+7 999 999-99-99",
                avatarURL = "",
                hasAvatar = false
            )
            val myMeetings =
                MoreContainerData(iconId = R.drawable.icon_meeting, textId = R.string.my_meetings, navigationRoute = NavGraphNodes.MoreRoot.MyMeetings.route)
            val theme =
                MoreContainerData(iconId = R.drawable.icon_theme, textId = R.string.theme_string)
            val notification =
                MoreContainerData(
                    iconId = R.drawable.icon_notification,
                    textId = R.string.notifications
                )
            val safety =
                MoreContainerData(iconId = R.drawable.icon_safety, textId = R.string.safety)
            val memory =
                MoreContainerData(iconId = R.drawable.icon_res, textId = R.string.memory_res_string)
            val help = MoreContainerData(iconId = R.drawable.icon_help, textId = R.string.help)
            val invite =
                MoreContainerData(iconId = R.drawable.icon_invite, textId = R.string.invite_friend)

            val listOfContainers =
                listOf(myMeetings, theme, notification, safety, memory, help, invite)

            _viewState.postValue(
                MoreScreenViewState.Display(
                    user = user,
                    containerList = listOfContainers
                )
            )
        }
    }
}