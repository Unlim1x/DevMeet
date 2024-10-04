package ru.lim1x.repository.implementations.community_repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.lim1x.domain.interfaces.repositories.ICommunityRepository
import ru.lim1x.domain.models.Community
import ru.lim1x.domain.models.CommunityDetailed
import ru.lim1x.repository.mock_source.MockDataSource

internal class CommunityRepository(private val dataSource:MockDataSource):ICommunityRepository {
    private val communitiesStateFlow:MutableStateFlow<List<Community>> = MutableStateFlow(emptyList())
    override fun loadCommunities(): StateFlow<List<Community>> {
        communitiesStateFlow.update { dataSource.getCommunities() }
        return communitiesStateFlow
    }

    override fun loadCommunityById(communityId: Int): StateFlow<CommunityDetailed> {
        return MutableStateFlow(dataSource.getCommunityInfo(communityId = communityId))
    }
}