package ru.lim1x.repository.event_repository

import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.models.CommunityRail
import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.PersonRail
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


    override fun loadMoreEvents(limit: Int, skip: Int): List<Event> {
        return dataSource.loadMoreEvents(limit, skip).mapEventListToDomain()
    }

    override fun loadRailCommunity(): CommunityRail {
        return dataSource.loadCommunityRail().mapCommunityRailToDomain()
    }


    override fun loadRailPersons(): PersonRail {
        return dataSource.fakePersonsToKnow().mapToDomain()
    }
}