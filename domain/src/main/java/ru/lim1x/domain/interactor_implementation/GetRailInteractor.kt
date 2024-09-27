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
    override fun execute(): Flow<MutableList<Rail>> {
        return useCase.observe()
    }
}

internal class LoadRailInteractor() : ILoadRailInteractor, KoinComponent {
    private val innerLoadRail: InnerLoadRailInteractor by inject()
    override fun execute() {
        innerLoadRail.loadRail()
    }
}

internal class InnerLoadRailInteractor() : KoinComponent {
    private val loadingRequestFlow = MutableStateFlow<Int>(-1)

    fun loadRail() {
        loadingRequestFlow.tryEmit(1)
    }

    fun observe() = loadingRequestFlow
}

internal class GetRailUseCase(private val repository: IEventRepository) : KoinComponent {
    private val railFlow = MutableStateFlow<MutableList<Rail>>(mutableListOf())
    private val innerLoadRail: InnerLoadRailInteractor by inject()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observe() = innerLoadRail.observe().mapLatest { railType ->
        println("RAILLOAD CALLED AND UPDATING FLOW")
        if (railType > 0) {
                railFlow.update {
                    it.apply {
                        add(
                            Rail(
                                railType = RailType.Community,
                                content = repository.loadRailCommunity()
                            )
                        )
                        add(Rail(railType = RailType.Banner, content = Banner.Default))
                        add(
                            Rail(
                                railType = RailType.Person,
                                content = repository.loadRailPersons()
                            )
                        )
                        add(
                            Rail(
                                railType = RailType.Community,
                                content = repository.loadRailCommunity()
                            )
                        )
                    }


        }

    }
        railFlow.value
    }
}