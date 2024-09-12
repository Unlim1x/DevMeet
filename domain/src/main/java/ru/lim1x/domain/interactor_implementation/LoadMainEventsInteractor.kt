package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.interactors.ILoadMainEventsInteractor
import ru.lim1x.domain.interfaces.repositories.IEventRepository

internal class LoadMainEventsInteractor(private val useCase: GetMainEventsUseCase) :
    ILoadMainEventsInteractor, KoinComponent {
    private val innerMainEventInteractor: InnerMainEventInteractor by inject()
    override fun execute() {
        innerMainEventInteractor.loadMainEvents()
    }
}

internal class InnerMainEventInteractor() : KoinComponent {
    private val userActionFlow = MutableStateFlow(0)
    fun loadMainEvents() {
        userActionFlow.tryEmit(1)
    }

    fun observe() = userActionFlow
}

internal class GetMainEventsUseCase(private val repository: IEventRepository) : KoinComponent {
    private val mainEventsFlow = MutableStateFlow(repository.loadMainEvents())
    private val innerInteractor: InnerMainEventInteractor by inject()

    fun observe() = innerInteractor.observe().mapLatest {
        mainEventsFlow.value
    }
}