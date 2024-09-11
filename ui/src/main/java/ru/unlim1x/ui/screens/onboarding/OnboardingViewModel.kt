package ru.unlim1x.ui.screens.onboarding

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.interactors.ITagsOnboardingGetInteractor
import ru.lim1x.domain.interfaces.interactors.ITagsOnboardingUpdateInteractor
import ru.unlim1x.old_ui.screens.MainViewModel
import ru.unlim1x.ui.kit.tag.TagUi
import ru.unlim1x.ui.mappers.mapTagToUi

internal class OnboardingViewModel(
    private val tagsOnboardingGetInteractor: ITagsOnboardingGetInteractor,
    private val tagsOnboardingUpdateInteractor: ITagsOnboardingUpdateInteractor,
) : MainViewModel<OnboardingEvent, OnboardingViewState>() {
    override val _viewState: MutableStateFlow<OnboardingViewState> =
        MutableStateFlow(OnboardingViewState.Loading)

    override fun viewState(): StateFlow<OnboardingViewState> {
        return _viewState
    }

    init {
        viewModelScope.launch {
            tagsOnboardingGetInteractor.execute().collect { tagList ->
                Log.d("VM", "Data collected: $tagList")
                val tagUis = tagList.mapTagToUi()
                _viewState.value = OnboardingViewState.Display(
                    tagUiList = tagUis,
                    isButtonActive = tagUis.any { tag -> tag.isSelected }
                )
                Log.e("VM", "_viewState.value = ${_viewState.value}")
            }
        }
    }

    override fun obtain(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.OnMainButtonClick -> {}
            OnboardingEvent.OnSkipButtonClick -> {}
            is OnboardingEvent.OnTagClick -> {
                Log.e("VM", "pressed called")
                tagsOnboardingUpdateInteractor.execute(event.tagUi.id)
            }
        }
    }


}