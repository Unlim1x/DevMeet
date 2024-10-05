package ru.lim1x.domain.interfaces.repositories

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Coordinates

interface IMapRepository {
    fun getMapUrlAndCoordinates(address: String): Flow<Pair<String?, Coordinates>>
    fun getNearestSubwayName(address: String): Flow<String?>
}