package ru.lim1x.domain.usecase_implementation.communities

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.interfaces.repositories.ICommunityRepository
import ru.lim1x.domain.interfaces.usecases.IGetCommunitiesUseCase
import ru.lim1x.domain.models.Community

internal class GetCommunitiesUseCase(private val commRepository:ICommunityRepository): IGetCommunitiesUseCase {
    override fun execute(): StateFlow<List<Community>> {
        return commRepository.loadCommunities()
    }
}