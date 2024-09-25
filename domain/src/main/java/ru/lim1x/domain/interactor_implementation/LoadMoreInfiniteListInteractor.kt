package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
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
import ru.lim1x.domain.models.Tag

internal class LoadMoreInfiniteListInteractor : ILoadMoreInfiniteListInteractor, KoinComponent {
    private val innerInteractor: InnerMoreEvents by inject()
    override fun execute(currentLoadedListSize: Int) {
        println("LoadMoreInfiniteListInteractor execute")
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
        return getInfiniteListUseCase.observeInfiniteList2()
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
    private val loadRailInteractor: ILoadRailInteractor by inject()
    val getTagsUseCase: GetTagsInfiniteListUseCase by inject()
    private var limit = 3
    private var skip = 0
    private var previousLoadedSize = -1

    //private val flowMoreEvents = MutableStateFlow(emptyList<Event>())
    private val previousListEvent: MutableList<Event> = mutableListOf()
    private val previousListTags: MutableList<Tag> = mutableListOf()


    var hasMoreData = true // Флаг для отслеживания, есть ли еще данные для загрузки

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun observeInfiniteList2(): Flow<Pair<List<Event>, List<Tag>>> =
        innerInteractor.observeLoadedListSizeFlow()
            .debounce(500)
            //.mapLatest { loadedSize -> loadedSize }
            .combine(innerTags.getTags()) { loadedSize, tags ->
                println("PREVIOUS SIZE: $previousLoadedSize, CURRENTSIZE: $loadedSize")
                println("TAGS CAME: $tags")
                val chosenTags = tags.filter { it.isSelected }

                // Проверяем, есть ли еще данные для загрузки
                if (loadedSize != -1) {
                    println("EXECUTED LOAD MORE EVENTS")
                    println("TAGS: $chosenTags")
                    //flowMoreEvents.update {
                    val list = repository.loadMoreEvents(limit, skip, chosenTags)

                    // Если пришел пустой список, останавливаем дальнейшие запросы
                    if (list.isEmpty()) {
                        hasMoreData = false
                    } else {
                        when (skip) {
                            0 -> loadRailInteractor.execute(railType = RailType.Banner)
                            3 -> loadRailInteractor.execute(railType = RailType.Person)
                            6 -> loadRailInteractor.execute(railType = RailType.Community)
                            else -> {}
                        }

                        if (previousListTags.isEmpty()) {
                            println("\nIN EMPTY\n")
                            skip += limit
                            previousListTags.addAll(chosenTags)
                            previousListEvent.addAll(list)
                            previousLoadedSize = previousListEvent.size
                        } else if (previousListTags == chosenTags) {
                            println("\nIN EQUAL TAGS\n")
                            skip += limit
                            previousListEvent.addAll(list)
                            previousLoadedSize = previousListEvent.size
                        } else {
                            println("\nIN ELSE\n")
                            skip = 0
                            previousListTags.clear()
                            previousListTags.addAll(chosenTags)
                            previousLoadedSize = loadedSize
                            previousListEvent.clear()
                            previousListEvent.addAll(list)
                        }
                    }

                    //}
                } else {
                    loadRailInteractor.execute(railType = RailType.Community)
                }
                println("AFTER EXECUTED LOAD MORE EVENTS SIZE: ${previousListEvent.size}")
                Pair(previousListEvent, tags)
            }

}

internal class FullInfo : KoinComponent, IGetMainScreenFullInfo {
    private val loadRailInteractor: ILoadRailInteractor by inject()

    init {
        loadRailInteractor.execute(railType = RailType.Community)
    }

    val getMoreEventsUseCase: IGetMoreEventsInteractor by inject()
    val getMainEventsUseCase: GetMainEventsUseCase by inject()
    val getSoonEventsUseCase: GetSoonEventsUseCase by inject()
    val getRailUseCase: GetRailUseCase by inject()
    // val getTagsUseCase: GetTagsInfiniteListUseCase by inject()

    private var lastState: MainState? = null


    private val innerFlow = combine(
        getMainEventsUseCase.observe(),
        getSoonEventsUseCase.observe(),
        getRailUseCase.observe(),
        getMoreEventsUseCase.execute()//.distinctUntilChanged { oldList, newList ->
        //oldList.first.hashCode() == newList.first.hashCode()
        //},
        //getTagsUseCase.observeTags()
    ) { it1, it2, it3, it4 ->
        val currentLastState = lastState // Локальная копия lastState
        if (currentLastState == null) {
            println("null combining new state!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            lastState = MainState(
                mainEventsList = it1,
                soonEventsList = it2,
                railList = mutableListOf(it3),
                otherTags = it4.second,
                infiniteEventsListByTag = it4.first
            )
            lastState!!
        } else {
            println("combining new state!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            println("tags!!!! ${it4.second}")
            lastState = MainState(
                mainEventsList = it1,
                soonEventsList = it2,
                railList = currentLastState.railList.toMutableList().apply { add(it3) },
                //railList = mutableListOf(it3),
                otherTags = it4.second,
                infiniteEventsListByTag = it4.first
            )
            lastState!!
        }


    }

    override fun invoke() = innerFlow

}
