package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.interactors.ILoadSoonEventsInteractor
import ru.lim1x.domain.interfaces.repositories.IEventRepository

internal class LoadSoonEventsInteractor() :
    ILoadSoonEventsInteractor, KoinComponent {
    private val innerMainEventInteractor: InnerSoonEventInteractor by inject()
    override fun execute() {
        innerMainEventInteractor.loadMainEvents()
    }
}

internal class InnerSoonEventInteractor() : KoinComponent {
    private val userActionFlow = MutableStateFlow(0)
    fun loadMainEvents() {
        userActionFlow.tryEmit(1)
    }

    fun observe() = userActionFlow
}

internal class GetSoonEventsUseCase(private val repository: IEventRepository) : KoinComponent {
    private val soonEventsFlow = MutableStateFlow(repository.loadSoonEvents())
    private val innerInteractor: InnerSoonEventInteractor by inject()

    fun observe() = innerInteractor.observe().mapLatest {
        soonEventsFlow.value
    }
}