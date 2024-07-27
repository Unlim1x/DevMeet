package ru.lim1x.domain.usecase_implementation.communities

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.interfaces.repositories.ICommunityRepository
import ru.lim1x.domain.interfaces.usecases.IGetCommunityDetailedInfoByIdUseCase
import ru.lim1x.domain.models.CommunityDetailed

internal class GetCommunityDetailedInfoByIdUseCase(private val commRepository: ICommunityRepository): IGetCommunityDetailedInfoByIdUseCase {
    override suspend fun execute(communityId: Int): Flow<CommunityDetailed> {
        return commRepository.loadCommunityById(communityId)
    }
}