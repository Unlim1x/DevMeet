package ru.unlim1x.ui.screens.onboarding

import ru.unlim1x.ui.kit.tag.TagUi

internal sealed class OnboardingEvent {
    data class OnTagClick(val tagUi: TagUi) : OnboardingEvent()
    data object OnMainButtonClick : OnboardingEvent()
    data object OnSkipButtonClick : OnboardingEvent()
}