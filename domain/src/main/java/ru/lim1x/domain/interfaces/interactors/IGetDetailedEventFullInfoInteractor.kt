package ru.lim1x.domain.interfaces.interactors

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.EventDetailed

interface IGetDetailedEventFullInfoInteractor {
    fun invoke(): Flow<EventDetailed>
}