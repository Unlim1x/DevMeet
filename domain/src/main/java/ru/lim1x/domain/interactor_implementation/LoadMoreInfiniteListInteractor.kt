package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.interactors.IGetMainScreenFullInfo
import ru.lim1x.domain.interfaces.interactors.IGetMoreEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadMoreInfiniteListInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadRailInteractor
import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.MainState
import ru.lim1x.domain.models.RailType

internal class LoadMoreInfiniteListInteractor : ILoadMoreInfiniteListInteractor, KoinComponent {
    private val innerInteractor: InnerMoreEvents by inject()
    override fun execute(currentLoadedListSize: Int) {
        println("LoadMoreInfiniteListInteractor execute")
        innerInteractor.loadMore(currentLoadedListSize)
    }
}

internal class InnerMoreEvents() : KoinComponent {
    private val loadedListSizeFlow: MutableSharedFlow<Int> = MutableStateFlow(-1)
    private val getInfiniteListUseCase: LoadInfiniteListUseCase by inject()

    fun observeLoadedListSizeFlow() = loadedListSizeFlow.asSharedFlow()

    fun loadMore(currentLoadedListSize: Int) {
        loadedListSizeFlow.tryEmit(currentLoadedListSize)
    }

    fun getFlow(): Flow<List<Event>> {
        return getInfiniteListUseCase.observeInfiniteList()
    }


}

internal class GetMoreEventsInteractor : IGetMoreEventsInteractor, KoinComponent {
    private val innerInteractor: InnerMoreEvents by inject()
    override fun execute(): Flow<List<Event>> {
        return innerInteractor.getFlow()
    }
}

internal class LoadInfiniteListUseCase : KoinComponent {
    private val repository: IEventRepository by inject()
    private val innerInteractor: InnerMoreEvents by inject()
    private val loadRailInteractor: ILoadRailInteractor by inject()
    private var limit = 3
    private var skip = 0
    private var previousLoadedSize = -1

    private val flowMoreEvents = MutableStateFlow(emptyList<Event>())

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun observeInfiniteList(): Flow<List<Event>> =
        innerInteractor.observeLoadedListSizeFlow().debounce(300).mapLatest { loadedSize ->
            println("PREVIOUS SIZE: $previousLoadedSize, CURRENTSIZE: $loadedSize")
            if (loadedSize != -1) {
                println("EXECUTED LOAD MORE EVENTS")
                flowMoreEvents.update {
                    val list = repository.loadMoreEvents(limit, skip)
                    when (skip) {
                        //0-> loadRailInteractor.execute(railType = RailType.Community)
                        0 -> loadRailInteractor.execute(railType = RailType.Banner)
                        3 -> loadRailInteractor.execute(railType = RailType.Person)
                        6 -> loadRailInteractor.execute(railType = RailType.Community)
                        else -> {}
                    }
                    skip += limit
                    previousLoadedSize = loadedSize

                    list
                }
            } else {
                loadRailInteractor.execute(railType = RailType.Community)
            }
            flowMoreEvents.value
        }
}

internal class FullInfo : KoinComponent, IGetMainScreenFullInfo {
    val getMoreEventsUseCase: LoadInfiniteListUseCase by inject()
    val getMainEventsUseCase: GetMainEventsUseCase by inject()
    val getSoonEventsUseCase: GetSoonEventsUseCase by inject()
    val getRailUseCase: GetRailUseCase by inject()
    val getTagsUseCase: GetTagsUseCase by inject()

    private var lastState: MainState? = null


    private val innerFlow = combine(
        getMainEventsUseCase.observe(),
        getSoonEventsUseCase.observe(),
        getRailUseCase.observe(),
        getMoreEventsUseCase.observeInfiniteList(),
        getTagsUseCase.observeTags()
    ) { it1, it2, it3, it4, it5 ->
        val currentLastState = lastState // Локальная копия lastState
        if (currentLastState == null) {
            lastState = MainState(
                mainEventsList = it1,
                soonEventsList = it2,
                railList = mutableListOf(it3),
                otherTags = it5,
                infiniteEventsListByTag = it4
            )
            lastState!!
        } else {
            lastState = MainState(
                mainEventsList = it1,
                soonEventsList = it2,
                railList = currentLastState.railList.toMutableList().apply { add(it3) },
                //railList = mutableListOf(it3),
                otherTags = it5,
                infiniteEventsListByTag = currentLastState.infiniteEventsListByTag.toMutableList()
                    .apply { addAll(it4) }
            )
            lastState!!
        }


    }

    override fun invoke() = innerFlow

}
