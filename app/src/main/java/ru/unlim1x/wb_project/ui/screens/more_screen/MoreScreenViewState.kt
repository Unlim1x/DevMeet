package ru.unlim1x.wb_project.ui.screens.more_screen

import ru.lim1x.domain.models.User
import ru.unlim1x.wb_project.ui.screens.more_screen.model.MoreContainerData

internal sealed class MoreScreenViewState {
    data object Init : MoreScreenViewState()
    data class Display(val user: User, val containerList: List<MoreContainerData>) :
        MoreScreenViewState()

}