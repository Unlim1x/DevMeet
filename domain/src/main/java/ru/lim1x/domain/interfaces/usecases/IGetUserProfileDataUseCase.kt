package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.models.User

interface IGetUserProfileDataUseCase {
   fun execute(userId:Int): StateFlow<User>
}