package ru.lim1x.repository.event_repository

import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.models.CommunityRail
import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.Person
import ru.lim1x.repository.mappers.mapCommunityRailListToDomain
import ru.lim1x.repository.mappers.mapEventListToDomain
import ru.lim1x.repository.mappers.mapPersonToDomain
import ru.lim1x.repository.mock_source.FakeDataSource

internal class EventRepository(private val dataSource: FakeDataSource) : IEventRepository {
    override fun loadMainEvents(): List<Event> {
        return dataSource.getMainEvents().mapEventListToDomain()
    }

    override fun loadSoonEvents(): List<Event> {
        return dataSource.getSoonEvents().mapEventListToDomain()
    }

    override fun loadRail(): List<CommunityRail> {
        return dataSource.loadCommunityRail().mapCommunityRailListToDomain()
    }

    override fun loadMaybeKnow(): List<Person> {
        return dataSource.fakePersonsToKnow().mapPersonToDomain()
    }

    override fun loadMoreEvents(limit: Int, skip: Int): List<Event> {
        return dataSource.loadMoreEvents(limit, skip).mapEventListToDomain()
    }
}