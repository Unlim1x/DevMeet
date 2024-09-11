package ru.unlim1x.ui.screens.onboarding

import ru.unlim1x.ui.kit.tag.TagUi

sealed class OnboardingViewState {
    data object Loading : OnboardingViewState()
    data class Display(val tagUiList: List<TagUi>, val isButtonActive: Boolean) :
        OnboardingViewState()
}