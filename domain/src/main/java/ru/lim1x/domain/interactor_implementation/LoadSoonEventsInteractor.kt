package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import ru.lim1x.domain.interfaces.interactors.ILoadMainEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadSoonEventsInteractor
import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.models.Event

internal class LoadSoonEventsInteractor() :
    ILoadSoonEventsInteractor, KoinComponent {
    private val useCase: GetSoonEventsUseCase by inject()
    override fun execute(): Flow<List<Event>> {
        return useCase.observe()
    }
}

internal class GetSoonEventsUseCase(private val repository: IEventRepository) : KoinComponent {
    private val soonEventsFlow = MutableStateFlow(repository.loadSoonEvents())

    fun observe() = soonEventsFlow.asStateFlow()
}