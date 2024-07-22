package ru.lim1x.domain.usecase_implementation.profile

import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.domain.interfaces.usecases.IGetCurrentUserIdUseCase

internal class GetCurrentUserIdIdUseCase(private val profileRepository: IProfileRepository) : IGetCurrentUserIdUseCase{
    override suspend fun execute(): Int {
        return profileRepository.userId()
    }
}