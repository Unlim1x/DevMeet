package ru.lim1x.domain.usecase_implementation.profile

import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.domain.interfaces.usecases.IGetCurrentUserIdUseCase

internal class GetCurrentUserIdIdUseCase(private val profileRepository: IProfileRepository) : IGetCurrentUserIdUseCase{
    override fun execute(): Int {
        return profileRepository.userId()
    }
}