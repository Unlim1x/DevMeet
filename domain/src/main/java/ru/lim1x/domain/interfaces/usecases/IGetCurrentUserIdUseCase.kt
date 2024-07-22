package ru.lim1x.domain.interfaces.usecases

interface IGetCurrentUserIdUseCase {
    suspend fun execute(): Int
}