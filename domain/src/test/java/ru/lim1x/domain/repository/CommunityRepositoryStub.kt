package ru.lim1x.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.lim1x.domain.interfaces.repositories.ICommunityRepository
import ru.lim1x.domain.models.Community
import ru.lim1x.domain.models.CommunityDetailed

class CommunityRepositoryStub(private val dataSource:DataSourceTest): ICommunityRepository {
    private val communitiesStateFlow: MutableStateFlow<List<Community>> =
        MutableStateFlow(emptyList())

    override fun loadCommunities(): StateFlow<List<Community>> {
        communitiesStateFlow.update { dataSource.getCommunities() }
        return communitiesStateFlow
    }

    override fun loadCommunityById(communityId: Int): StateFlow<CommunityDetailed> {
        return MutableStateFlow(dataSource.getCommunityInfo(communityId = communityId))
    }
}