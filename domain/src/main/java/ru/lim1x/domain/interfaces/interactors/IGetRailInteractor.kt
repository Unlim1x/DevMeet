package ru.lim1x.domain.interfaces.interactors

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Rail

interface IGetRailInteractor {
    fun execute(): Flow<Rail>
}