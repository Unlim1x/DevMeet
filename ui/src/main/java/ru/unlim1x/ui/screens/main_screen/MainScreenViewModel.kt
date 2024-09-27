package ru.unlim1x.ui.screens.main_screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.lim1x.domain.interfaces.interactors.IGetMainScreenFullInfo
import ru.lim1x.domain.interfaces.interactors.ILoadMoreInfiniteListInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadRailInteractor
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

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainScreenViewModel(
    private val getFullInfo: IGetMainScreenFullInfo,
    private val loadMoreListInteractor: ILoadMoreInfiniteListInteractor,
    private val updateTagInteractor: ITagsInfiniteListUpdateInteractor,
    private val loadRails: ILoadRailInteractor,
) : MainViewModel<MainScreenEvent, MainScreenViewState>() {
    override val _viewState: MutableStateFlow<MainScreenViewState> =
        MutableStateFlow(MainScreenViewState.Loading)

    private val userActionFlow = MutableSharedFlow<MainScreenEvent>(replay = 1)

    init {
        subscribeOnUserActions()
        viewModelScope.launch { loadRails.execute() }
        getFullInfo.invoke().onEach { mainState ->
            _viewState.update {
                MainScreenViewState.Display(
                    mainEventsList = mainState.mainEventsList.mapEventListToUi(),
                    soonEventsList = mainState.soonEventsList.mapEventListToUi(),
                    otherTags = mainState.otherTags.mapTagToUi(),
                    railList = mapRail(mainState.railList),
                    infiniteEventsListByTag = mainState.infiniteEventsListByTag.mapEventListToUi()
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



    private fun subscribeOnUserActions() {
        userActionFlow.onEach {
            when (it) {
                is MainScreenEvent.ClickOnCommunity -> TODO()
                is MainScreenEvent.ClickOnCommunitySubscribe -> TODO()
                is MainScreenEvent.ClickOnEvent -> TODO()
                MainScreenEvent.ScrolledToEndOfList -> {
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
        userActionFlow.tryEmit(event)
    }


    override fun viewState(): StateFlow<MainScreenViewState> {
        return _viewState
    }
}