package ru.lim1x.repository.community_repository

import android.content.Context
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import ru.lim1x.domain.interfaces.repositories.ICommunityRepository
import ru.lim1x.domain.models.Community
import ru.lim1x.domain.models.CommunityDetailed
import ru.lim1x.repository.mock_source.MockDataSource
import kotlin.coroutines.coroutineContext

internal class CommunityRepository(private val dataSource:MockDataSource):ICommunityRepository {
    override fun loadCommunities(): StateFlow<List<Community>> {
        return MutableStateFlow(dataSource.getCommunities())
    }

    override fun loadCommunityById(communityId: Int): StateFlow<CommunityDetailed> {
        return MutableStateFlow(dataSource.getCommunityInfo(communityId = communityId))
    }
}