package ru.lim1x.domain.usecase_implementation.profile

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.domain.interfaces.usecases.IGetUserProfileDataUseCase
import ru.lim1x.domain.models.User

internal class GetUserProfileDataUseCase(private val profileRepository:IProfileRepository): IGetUserProfileDataUseCase {
    override suspend fun execute(userId: Int): Flow<User> {
        return profileRepository.loadProfile(userId)
    }
}