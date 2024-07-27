package ru.lim1x.domain.usecase_implementation.communities

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.interfaces.repositories.ICommunityRepository
import ru.lim1x.domain.interfaces.usecases.IGetCommunitiesUseCase
import ru.lim1x.domain.models.Community

internal class GetCommunitiesUseCase(private val commRepository:ICommunityRepository): IGetCommunitiesUseCase {
    override suspend fun execute(): Flow<List<Community>> {
        return commRepository.loadCommunities()
    }
}