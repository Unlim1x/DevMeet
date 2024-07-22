package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.User

interface IGetUserProfileDataUseCase {
    suspend fun execute(userId:Int): Flow<User>
}