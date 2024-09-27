package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.interactors.IGetMainScreenFullInfo
import ru.lim1x.domain.interfaces.interactors.IGetMoreEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadMoreInfiniteListInteractor
import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.MainState
import ru.lim1x.domain.models.Tag

internal class LoadMoreInfiniteListInteractor : ILoadMoreInfiniteListInteractor, KoinComponent {
    private val innerInteractor: InnerMoreEvents by inject()
    override fun execute(currentLoadedListSize: Int) {
        innerInteractor.loadMore(currentLoadedListSize)
    }
}

internal class InnerMoreEvents : KoinComponent {
    private val loadedListSizeFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)
    private val getInfiniteListUseCase: LoadInfiniteListUseCase by inject()

    init {
        loadedListSizeFlow.tryEmit(-1)
    }

    fun observeLoadedListSizeFlow() = loadedListSizeFlow.asSharedFlow()

    fun loadMore(currentLoadedListSize: Int) {
        loadedListSizeFlow.tryEmit(currentLoadedListSize)
    }

    fun getFlow(): Flow<Pair<List<Event>, List<Tag>>> {
        return getInfiniteListUseCase.prepareFlow()
    }


}

internal class GetMoreEventsInteractor : IGetMoreEventsInteractor, KoinComponent {
    private val innerInteractor: InnerMoreEvents by inject()
    override fun execute(): Flow<Pair<List<Event>, List<Tag>>> {
        return innerInteractor.getFlow()
    }
}

internal class LoadInfiniteListUseCase : KoinComponent {
    private val innerTags: InnerTagsInfiniteListInteractor by inject()
    private val repository: IEventRepository by inject()
    private val innerInteractor: InnerMoreEvents by inject()
    private var limit = 10
    private var skip = 0
    private var previousLoadedSize = -1


    private val flowMoreEvents: MutableStateFlow<Pair<List<Event>, List<Tag>>> =
        MutableStateFlow(Pair(emptyList(), emptyList()))
    private var previousListEvent: MutableList<Event> = mutableListOf()
    private val previousListTags: MutableList<Tag> = mutableListOf()


    private var hasMoreData = true


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun prepareFlow() =
        innerInteractor.observeLoadedListSizeFlow()
            .debounce(500)
            .combine(innerTags.getTags()) { loadedSize, tags ->
                val chosenTags = tags.filter { it.isSelected }
                if (previousListTags == chosenTags) {
                    skip += limit
                } else {
                    hasMoreData = true
                    skip = 0
                    previousListTags.clear()
                    previousListTags.addAll(chosenTags)
                    previousListEvent.clear()
                }

                if (loadedSize != -1 && hasMoreData) {
                    val list = repository.loadMoreEvents(limit, skip, chosenTags)


                    if (list.isEmpty()) {
                        hasMoreData = false
                    } else {
                        previousListEvent = mutableListOf<Event>().apply {
                            addAll(previousListEvent)
                            addAll(list)
                        }
                        previousLoadedSize = previousListEvent.size
                        flowMoreEvents.update { (Pair(previousListEvent, tags)) }

                    }


                }


            }.mapLatest {
                flowMoreEvents.value
            }



}

internal class FullInfo : KoinComponent, IGetMainScreenFullInfo {



    val getMoreEventsUseCase: IGetMoreEventsInteractor by inject()
    val getMainEventsUseCase: GetMainEventsUseCase by inject()
    val getSoonEventsUseCase: GetSoonEventsUseCase by inject()
    val getRailUseCase: GetRailUseCase by inject()

    private var lastState: MutableStateFlow<MainState?> = MutableStateFlow(null)


    private val innerFlow = combine(
        getMainEventsUseCase.observe(),
        getSoonEventsUseCase.observe(),
        getRailUseCase.observe(),
        getMoreEventsUseCase.execute()

    ) { it1, it2, it3, it4 ->
        lastState.update {
            MainState(
                mainEventsList = it1,
                soonEventsList = it2,
                railList = it3,
                otherTags = it4.second,
                infiniteEventsListByTag = it4.first
            )
        }
        lastState.value!!

    }.distinctUntilChanged { old, new ->
        old.infiniteEventsListByTag.size == new.infiniteEventsListByTag.size &&
                old.otherTags == new.otherTags
    }

    override fun invoke() = innerFlow

}
