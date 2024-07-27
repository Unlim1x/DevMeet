package ru.lim1x.domain.interfaces.repositories

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Community
import ru.lim1x.domain.models.CommunityDetailed

interface ICommunityRepository {
    suspend fun loadCommunities(): Flow<List<Community>>
    suspend fun loadCommunityById(communityId:Int): Flow<CommunityDetailed>

}