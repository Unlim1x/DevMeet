package ru.lim1x.domain.usecase_implementation.profile

import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.domain.interfaces.usecases.IGetUserAvatarByIdUseCase

internal class GetUserAvatarByIdUseCase(private val meetingRepository: IMeetingsRepository):IGetUserAvatarByIdUseCase {
    override fun execute(userId:Int):String{
        return meetingRepository.getUserAvatar(userId = userId)
    }
}