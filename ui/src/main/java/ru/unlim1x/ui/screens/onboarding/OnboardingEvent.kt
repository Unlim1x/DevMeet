package ru.unlim1x.ui.screens.onboarding

import ru.unlim1x.ui.kit.tag.Tag

internal sealed class OnboardingEvent {
    data class OnTagClick(val tag: Tag) : OnboardingEvent()
    data object OnMainButtonClick : OnboardingEvent()
    data object OnSkipButtonClick : OnboardingEvent()
}