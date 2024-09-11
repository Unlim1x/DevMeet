package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.interactors.ILoadMainEventsInteractor
import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.models.Event

internal class LoadMainEventsInteractor(private val useCase: GetMainEventsUseCase) :
    ILoadMainEventsInteractor, KoinComponent {

    override fun execute(): Flow<List<Event>> {
        return useCase.observe()
    }
}

internal class GetMainEventsUseCase(private val repository: IEventRepository) : KoinComponent {
    private val mainEventsFlow = MutableStateFlow(repository.loadMainEvents())

    fun observe() = mainEventsFlow.asStateFlow()
}