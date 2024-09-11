package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.interactors.ILoadMainEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadRailInteractor
import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.models.CommunityRail
import ru.lim1x.domain.models.Event

internal class LoadRailInteractor() :
    ILoadRailInteractor, KoinComponent {
    private val useCase: GetRailUseCase by inject()
    override fun execute(): Flow<List<CommunityRail>> {
        return useCase.observe()
    }
}

internal class InnerLoadRailInteractor() : KoinComponent {
    private val loadingRequestFlow = MutableStateFlow<Int>(0)

    fun loadRail(nextId: Int) {
        loadingRequestFlow.tryEmit(nextId)
    }

    fun observe() = loadingRequestFlow
}

internal class GetRailUseCase(private val repository: IEventRepository) : KoinComponent {
    private val railFlow = MutableStateFlow(repository.loadRail())
    private val innerLoadRail: InnerLoadRailInteractor by inject()
    fun observe() = innerLoadRail.observe().map {
        if (it > 0) {
            railFlow.update {
                val newList = it.toMutableList()
                newList.addAll(repository.loadRail())
                newList
            }
        }
        railFlow.value
    }
}