package ru.unlim1x.ui.screens.main_screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import ru.lim1x.domain.interfaces.interactors.IGetMoreEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadMainEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadMoreEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadRailInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadSoonEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ITagsOnboardingGetInteractor
import ru.unlim1x.old_ui.screens.MainViewModel
import ru.unlim1x.ui.mappers.mapCommunityRailListToUi
import ru.unlim1x.ui.mappers.mapEventListToUi
import ru.unlim1x.ui.mappers.mapTagToUi

internal class MainScreenViewModel(
    private val getMainEventsInteractor: ILoadMainEventsInteractor,
    private val getSoonEventsInteractor: ILoadSoonEventsInteractor,
    private val getRailInteractor: ILoadRailInteractor,
    private val tagsOnboardingGetInteractor: ITagsOnboardingGetInteractor,
    private val loadMoreEventsInteractor: ILoadMoreEventsInteractor,
    private val getMoreEventsInteractor: IGetMoreEventsInteractor,
) : MainViewModel<MainScreenEvent, MainScreenViewState>() {
    override val _viewState: MutableStateFlow<MainScreenViewState> =
        MutableStateFlow(MainScreenViewState.Loading)

    init {
        combine(
            getMainEventsInteractor.execute(),
            getSoonEventsInteractor.execute(),
            getRailInteractor.execute(),
            tagsOnboardingGetInteractor.execute()
        ) { it1, it2, it3, it4 ->
            _viewState.value = MainScreenViewState.Display(
                mainEventsList = it1.mapEventListToUi(),
                soonEventsList = it2.mapEventListToUi(),
                railList = it3.mapCommunityRailListToUi(),
                otherTags = it4.mapTagToUi(),
                infiniteEventsListByTag = mutableListOf()
            )
        }.launchIn(viewModelScope)

        getMoreEventsInteractor.execute().onEach {
            // Получение новых данных и обновление состояния
            Log.e("VM", "VM GOT of ${it.size} events list")
            if (it.isNotEmpty()) {
                val newEvents = it.mapEventListToUi()
                val updatedList = (_viewState.value as MainScreenViewState.Display)
                    .infiniteEventsListByTag.toMutableList().apply {
                        addAll(newEvents)
                    }
                _viewState.value = (_viewState.value as MainScreenViewState.Display).copy(
                    infiniteEventsListByTag = updatedList
                )
            }

        }.launchIn(viewModelScope)
    }
    override fun obtain(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.ClickOnCommunity -> TODO()
            is MainScreenEvent.ClickOnCommunitySubscribe -> TODO()
            is MainScreenEvent.ClickOnEvent -> TODO()
            MainScreenEvent.ScrolledToEndOfList -> {
                Log.e("VM", "CALLED LOAD MORE")
                loadMoreEventsInteractor.execute((_viewState.value as MainScreenViewState.Display).infiniteEventsListByTag.size)
            }
        }
    }


    override fun viewState(): StateFlow<MainScreenViewState> {
        return _viewState
    }
}