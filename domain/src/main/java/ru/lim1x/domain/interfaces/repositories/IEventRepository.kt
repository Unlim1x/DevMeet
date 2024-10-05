package ru.lim1x.domain.interfaces.repositories

import ru.lim1x.domain.models.CommunityRail
import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.EventDetailed
import ru.lim1x.domain.models.PersonRail
import ru.lim1x.domain.models.Tag

interface IEventRepository {
    fun loadMainEvents(): List<Event>
    fun loadSoonEvents(): List<Event>

    fun loadRailCommunity(): CommunityRail
    fun loadRailPersons(): PersonRail

    fun loadMoreEvents(limit: Int, skip: Int, tags: List<Tag>? = null): List<Event>

    fun getAllTags(): List<Tag>

    fun loadEventDetailedInfoById(id: Int): EventDetailed

}