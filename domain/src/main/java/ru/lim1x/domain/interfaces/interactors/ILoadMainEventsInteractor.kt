package ru.lim1x.domain.interfaces.interactors

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Event

interface ILoadMainEventsInteractor {
    fun execute(): Flow<List<Event>>
}