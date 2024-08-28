package ru.unlim1x.old_ui.uiKit.avatar.state

internal sealed interface UserAvatarState {
    data object Default : UserAvatarState
    data object Edit : UserAvatarState
}