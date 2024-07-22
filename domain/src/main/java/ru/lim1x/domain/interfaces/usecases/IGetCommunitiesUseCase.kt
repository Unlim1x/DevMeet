package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Community

interface IGetCommunitiesUseCase {
    suspend fun execute(): Flow<List<Community>>
}