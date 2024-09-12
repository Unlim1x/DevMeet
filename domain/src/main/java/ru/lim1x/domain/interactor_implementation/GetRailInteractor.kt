package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.interactors.IGetRailInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadRailInteractor
import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.models.Banner
import ru.lim1x.domain.models.Rail
import ru.lim1x.domain.models.RailType

internal class GetRailInteractor() :
    IGetRailInteractor, KoinComponent {
    private val useCase: GetRailUseCase by inject()
    override fun execute(): Flow<Rail> {
        return useCase.observe()
    }
}

internal class LoadRailInteractor() : ILoadRailInteractor, KoinComponent {
    private val innerLoadRail: InnerLoadRailInteractor by inject()
    override fun execute(railType: RailType) {
        innerLoadRail.loadRail(railType)
    }
}

internal class InnerLoadRailInteractor() : KoinComponent {
    private val loadingRequestFlow = MutableStateFlow<RailType>(RailType.Nothing)

    fun loadRail(railType: RailType) {
        loadingRequestFlow.tryEmit(railType)
    }

    fun observe() = loadingRequestFlow
}

internal class GetRailUseCase(private val repository: IEventRepository) : KoinComponent {
    private val railFlow = MutableStateFlow<Rail>(Rail(railType = RailType.Nothing, content = ""))
    private val innerLoadRail: InnerLoadRailInteractor by inject()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observe() = innerLoadRail.observe().mapLatest { railType ->
        println("RAILLOAD CALLED AND UPDATING FLOW")
        when (railType) {
            RailType.Community -> {
                railFlow.update {
                    Rail(railType = railType, content = repository.loadRailCommunity())
                }
            }

            RailType.Banner -> {
                railFlow.update {
                    Rail(railType = railType, content = Banner.Default)
                }
            }

            RailType.Person -> {
                railFlow.update {
                    Rail(railType = railType, content = repository.loadRailPersons())
                }
            }

            RailType.Nothing -> {}
        }
        railFlow.value
    }
}