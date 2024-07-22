package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.CommunityDetailed

interface IGetCommunityDetailedInfoByIdUseCase {
    suspend fun execute(communityId:Int): Flow<CommunityDetailed>
}