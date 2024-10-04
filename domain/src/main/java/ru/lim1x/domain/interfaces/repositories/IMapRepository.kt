package ru.lim1x.domain.interfaces.repositories

import kotlinx.coroutines.flow.Flow

interface IMapRepository {
    fun getMapUrl(address: String): Flow<String?>
    fun getNearestSubwayName(address: String): String
}