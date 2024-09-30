package ru.lim1x.domain.interfaces.interactors

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.SearchState

interface IGetMainScreenSearchState {
    fun invoke(): Flow<SearchState>
}