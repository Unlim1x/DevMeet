package ru.lim1x.domain.interactor_implementation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.lim1x.domain.interfaces.interactors.IGetDetailedEventFullInfoInteractor
import ru.lim1x.domain.interfaces.interactors.IRequestDetailedEventFullInfoInteractor
import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.interfaces.repositories.IMapRepository
import ru.lim1x.domain.models.EventDetailed

internal class GetDetailedEventFullInfoInteractor : IRequestDetailedEventFullInfoInteractor,
    KoinComponent {
    private val innerDetailedInfoUseCase: InnerDetailedEventUseCase by inject()
    override fun invoke(id: Int) {
        innerDetailedInfoUseCase.loadDetailedEvent(id)
    }
}

internal class InnerDetailedEventUseCase() : KoinComponent {

    private val idFlow = MutableSharedFlow<Int>(replay = 1)
    fun loadDetailedEvent(id: Int) {
        idFlow.tryEmit(id)
    }

    fun observeInfo() = idFlow
}

internal class DetailedEventInfoFlowInteractor : KoinComponent,
    IGetDetailedEventFullInfoInteractor {
    private val innerFlow: InnerDetailedEventUseCase by inject()
    private val eventRepository: IEventRepository by inject()
    private val mapRepository: IMapRepository by inject()
    //private val detailedStateFlow: MutableStateFlow<EventDetailed?> = MutableStateFlow(null)

    override fun invoke() = innerFlow.observeInfo().flatMapLatest {
        val event = eventRepository.loadEventDetailedInfoById(it)

        combine(
            mapRepository.getMapUrlAndCoordinates(event.address),
            mapRepository.getNearestSubwayName(event.address)
        ) { it1, it2 ->
            val eventWithMap = event.copy(
                mapUrl = it1.first ?: "",
                nearestSubway = it2 ?: "",
                latitude = it1.second.latitude,
                longitude = it1.second.longitude
            )
            eventWithMap
        }

    }
}