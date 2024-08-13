package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.StateFlow

interface IGetCurrentUserIdUseCase {
    fun execute(): Int
}