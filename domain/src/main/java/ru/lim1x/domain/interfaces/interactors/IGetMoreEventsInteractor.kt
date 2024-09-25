package ru.lim1x.domain.interfaces.interactors

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.Tag

interface IGetMoreEventsInteractor {
    fun execute(): Flow<Pair<List<Event>, List<Tag>>>
}