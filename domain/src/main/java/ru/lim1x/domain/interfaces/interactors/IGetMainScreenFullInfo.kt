package ru.lim1x.domain.interfaces.interactors

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.MainState

interface IGetMainScreenFullInfo {
    fun invoke(): Flow<MainState>
}