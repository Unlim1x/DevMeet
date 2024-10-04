package ru.lim1x.repository.implementations.search_repository

import ru.lim1x.domain.interfaces.repositories.ISearchRepository
import ru.lim1x.domain.models.Event
import ru.lim1x.domain.models.Rail
import ru.lim1x.domain.models.RailType
import ru.lim1x.domain.models.SearchState
import ru.lim1x.repository.mappers.mapCommunityRailToDomain
import ru.lim1x.repository.mappers.mapEventListToDomain
import ru.lim1x.repository.mock_source.FakeDataSource

internal class SearchRepository(private val fakeDataSource: FakeDataSource) : ISearchRepository {
    override fun search(text: String): SearchState {
        val result = fakeDataSource.searchEvents(text)
        return SearchState(
            searchedEvents = result.first.mapEventListToDomain(),
            rail = Rail(RailType.Community, result.second.mapCommunityRailToDomain()),
            additionHeader = result.third.first,
            additionList = result.third.second.mapEventListToDomain()
        )
    }

    override fun searchMore(text: String, limit: Int, skip: Int): List<Event> {
        return fakeDataSource.searchMore(text, limit, skip).mapEventListToDomain()
    }
}