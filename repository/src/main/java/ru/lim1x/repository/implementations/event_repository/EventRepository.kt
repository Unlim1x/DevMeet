package ru.lim1x.repository.implementations.event_repository

import android.util.Log
import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.models.CommunityRail
import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.PersonRail
import ru.lim1x.domain.models.Tag
import ru.lim1x.repository.mappers.mapCommunityRailToDomain
import ru.lim1x.repository.mappers.mapEventListToDomain
import ru.lim1x.repository.mappers.mapToDomain
import ru.lim1x.repository.mock_source.FakeDataSource

internal class EventRepository(private val dataSource: FakeDataSource) : IEventRepository {
    override fun loadMainEvents(): List<Event> {
        return dataSource.getMainEvents().mapEventListToDomain()
    }

    override fun loadSoonEvents(): List<Event> {
        return dataSource.getSoonEvents().mapEventListToDomain()
    }


    override fun loadMoreEvents(limit: Int, skip: Int, tags: List<Tag>?): List<Event> {
        Log.e("TAGS", "AAAAA $tags, ${tags.isNullOrEmpty()}")
        return if (tags.isNullOrEmpty())
            dataSource.loadMoreEvents(limit, skip).mapEventListToDomain()
        else
            dataSource.loadMoreEvents(limit, skip, tags.map { it.id }).mapEventListToDomain()
    }

    override fun getAllTags(): List<Tag> {
        return dataSource.eventTags
    }

    override fun loadRailCommunity(): CommunityRail {
        return dataSource.loadCommunityRail().mapCommunityRailToDomain()
    }


    override fun loadRailPersons(): PersonRail {
        return dataSource.fakePersonsToKnow().mapToDomain()
    }


}