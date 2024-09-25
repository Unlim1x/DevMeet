package ru.unlim1x.ui.screens.main_screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.lim1x.domain.interfaces.interactors.IGetMainScreenFullInfo
import ru.lim1x.domain.interfaces.interactors.ILoadMoreInfiniteListInteractor
import ru.lim1x.domain.interfaces.interactors.ITagsInfiniteListUpdateInteractor
import ru.lim1x.domain.models.CommunityRail
import ru.lim1x.domain.models.PersonRail
import ru.lim1x.domain.models.Rail
import ru.lim1x.domain.models.RailType
import ru.unlim1x.old_ui.screens.MainViewModel
import ru.unlim1x.ui.mappers.mapCommunityRailToUi
import ru.unlim1x.ui.mappers.mapEventListToUi
import ru.unlim1x.ui.mappers.mapTagToUi
import ru.unlim1x.ui.mappers.mapToPersonRailUi

internal class MainScreenViewModel(
    private val getFullInfo: IGetMainScreenFullInfo,
    private val loadMoreListInteractor: ILoadMoreInfiniteListInteractor,
    private val updateTagInteractor: ITagsInfiniteListUpdateInteractor,
) : MainViewModel<MainScreenEvent, MainScreenViewState>() {
    override val _viewState: MutableStateFlow<MainScreenViewState> =
        MutableStateFlow(MainScreenViewState.Loading)

    private val stateMutex = Mutex()
    private val userActionFlow = MutableSharedFlow<MainScreenEvent>(replay = 1)

    init {
        subscribeOnUserActions()
        getFullInfo.invoke().onEach {
            safeUpdateStateForDisplay { currentState ->
                currentState.copy(
                    mainEventsList = it.mainEventsList.mapEventListToUi(),
                    soonEventsList = it.soonEventsList.mapEventListToUi(),
                    otherTags = it.otherTags.mapTagToUi(),
                    railList = mapRail(it.railList),
                    infiniteEventsListByTag = it.infiniteEventsListByTag.mapEventListToUi()
                )
            }
            safeUpdateStateForLoading { currentState ->
                MainScreenViewState.Display(
                    mainEventsList = it.mainEventsList.mapEventListToUi(),
                    soonEventsList = it.soonEventsList.mapEventListToUi(),
                    otherTags = it.otherTags.mapTagToUi(),
                    railList = mapRail(it.railList),
                    infiniteEventsListByTag = it.infiniteEventsListByTag.mapEventListToUi()
                )
            }
        }.launchIn(viewModelScope)


    }

    private fun mapRail(railList: List<Rail>): List<Rail> {
        return railList.map { rail ->
            when (rail.railType) {
                RailType.Community -> Rail(
                    railType = rail.railType,
                    content = (rail.content as CommunityRail).mapCommunityRailToUi()
                )

                RailType.Banner -> {
                    rail
                }

                RailType.Person -> Rail(
                    railType = rail.railType,
                    content = (rail.content as PersonRail).mapToPersonRailUi()
                )

                RailType.Nothing -> {
                    rail
                }
            }
        }
    }

    private suspend fun safeUpdateStateForDisplay(action: (MainScreenViewState.Display) -> MainScreenViewState.Display) {
        stateMutex.withLock {
            val currentState = _viewState.value
            if (currentState is MainScreenViewState.Display) {
                _viewState.value = action(currentState)
            }
        }
    }

    private suspend fun safeUpdateStateForLoading(action: (MainScreenViewState.Loading) -> MainScreenViewState.Display) {
        stateMutex.withLock {
            val currentState = _viewState.value
            if (currentState is MainScreenViewState.Loading) {
                _viewState.value = action(currentState)
            }
        }
    }

    private fun subscribeOnUserActions() {
        userActionFlow.onEach {
            Log.e("VM", "RECIEVED AN EVENT")
            when (it) {
                is MainScreenEvent.ClickOnCommunity -> TODO()
                is MainScreenEvent.ClickOnCommunitySubscribe -> TODO()
                is MainScreenEvent.ClickOnEvent -> TODO()
                MainScreenEvent.ScrolledToEndOfList -> {
                    Log.e("VM", "CALLED LOAD MORE")
                    loadMoreListInteractor.execute((_viewState.value as MainScreenViewState.Display).infiniteEventsListByTag.size)
                }

                is MainScreenEvent.ClickOnTag -> {
                    clickOnTag(it.tagId)
                }

                MainScreenEvent.Idle -> {}
            }

        }.launchIn(viewModelScope)
    }


    private fun clickOnTag(tagId: Int) {
        updateTagInteractor.execute(tagId)
    }


    override fun obtain(event: MainScreenEvent) {
        Log.e("VM", "EMITTING AN EVENT")
        userActionFlow.tryEmit(event)
    }


    override fun viewState(): StateFlow<MainScreenViewState> {
        return _viewState
    }
}