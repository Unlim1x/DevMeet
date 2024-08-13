package ru.unlim1x.wb_project.ui.uiKit.avatar.state

internal sealed interface UserAvatarState {
    data object Default : UserAvatarState
    data object Edit : UserAvatarState
}