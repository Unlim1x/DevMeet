package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.StateFlow

interface IGetUserAvatarByIdUseCase {
    fun execute(userId:Int): String?
}