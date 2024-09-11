package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.interactors.IGetMoreEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadMoreEventsInteractor
import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.models.Event

internal class LoadMoreEventsInteractor : ILoadMoreEventsInteractor, KoinComponent {
    private val innerInteractor: InnerMoreEvents by inject()
    override fun execute(currentLoadedListSize: Int) {
        println("LoadMoreEventsInteractor execute")
        innerInteractor.loadMore(currentLoadedListSize)
    }
}

internal class InnerMoreEvents() : KoinComponent {
    private val loadedListSizeFlow: MutableSharedFlow<Int> = MutableStateFlow(-1)
    private val getMoreEventsUseCase: GetMoreEventsUseCase by inject()

    fun observeLoadedListSizeFlow() = loadedListSizeFlow.asSharedFlow()

    fun loadMore(currentLoadedListSize: Int) {
        loadedListSizeFlow.tryEmit(currentLoadedListSize)
    }

    fun getFlow(): Flow<List<Event>> {
        return getMoreEventsUseCase.observeMoreEvents()
    }


}

internal class GetMoreEventsInteractor : IGetMoreEventsInteractor, KoinComponent {
    private val innerInteractor: InnerMoreEvents by inject()
    override fun execute(): Flow<List<Event>> {
        return innerInteractor.getFlow()
    }
}

internal class GetMoreEventsUseCase : KoinComponent {
    private val repository: IEventRepository by inject()
    private val innerInteractor: InnerMoreEvents by inject()
    private val innerRailInteractor: InnerLoadRailInteractor by inject()
    private var limit = 3
    private var skip = 0
    private var previousLoadedSize = -1

    private val flowMoreEvents = MutableStateFlow(emptyList<Event>())

    fun observeMoreEvents(): Flow<List<Event>> =
        innerInteractor.observeLoadedListSizeFlow().mapLatest { loadedSize ->
            if (previousLoadedSize != loadedSize) {
                println("EXECUTED LOAD MORE EVENTS")
                if (previousLoadedSize == 9) {
                    //innerRailInteractor.loadRail(1)
                }
                flowMoreEvents.update {
                    val list = repository.loadMoreEvents(limit, skip)
                    skip += limit
                    previousLoadedSize = loadedSize
                    list
                }
            }
            flowMoreEvents.value
        }
}
