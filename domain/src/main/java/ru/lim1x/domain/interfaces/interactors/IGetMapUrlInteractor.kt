package ru.lim1x.domain.interfaces.interactors

import kotlinx.coroutines.flow.Flow

interface IGetMapUrlInteractor {
    fun execute(address: String): Flow<String?>
}