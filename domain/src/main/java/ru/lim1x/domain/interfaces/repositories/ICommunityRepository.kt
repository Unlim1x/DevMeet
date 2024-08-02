package ru.lim1x.domain.interfaces.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.models.Community
import ru.lim1x.domain.models.CommunityDetailed

interface ICommunityRepository {
    fun loadCommunities(): StateFlow<List<Community>>
    fun loadCommunityById(communityId:Int): StateFlow<CommunityDetailed?>

}