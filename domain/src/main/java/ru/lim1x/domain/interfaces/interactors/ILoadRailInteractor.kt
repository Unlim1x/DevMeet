package ru.lim1x.domain.interfaces.interactors

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.CommunityRail

interface ILoadRailInteractor {
    fun execute(): Flow<List<CommunityRail>>
}