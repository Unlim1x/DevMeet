package ru.lim1x.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.lim1x.domain.interfaces.repositories.ICommunityRepository
import ru.lim1x.domain.models.Community
import ru.lim1x.domain.models.CommunityDetailed

class CommunityRepositoryStub(private val dataSource:DataSourceTest): ICommunityRepository {
    override suspend fun loadCommunities(): Flow<List<Community>> {
        return flowOf(dataSource.getCommunities())
    }

    override suspend fun loadCommunityById(communityId: Int): Flow<CommunityDetailed> {
        return flowOf(dataSource.getCommunityInfo(communityId = communityId))
    }
}