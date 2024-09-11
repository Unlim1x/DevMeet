package ru.lim1x.domain.interfaces.repositories

import ru.lim1x.domain.models.CommunityRail
import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.Person

interface IEventRepository {
    fun loadMainEvents(): List<Event>
    fun loadSoonEvents(): List<Event>

    fun loadRail(): List<CommunityRail>

    fun loadMaybeKnow(): List<Person>

    fun loadMoreEvents(limit: Int, skip: Int): List<Event>
}