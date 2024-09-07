package ru.unlim1x.ui.screens.onboarding

import ru.unlim1x.ui.kit.tag.Tag

sealed class OnboardingViewState {
    data object Loading : OnboardingViewState()
    data class Display(val tagList: List<Tag>, val isButtonActive: Boolean) : OnboardingViewState()
}