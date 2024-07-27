package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Meeting

interface  IGetAllMeetingsUseCase {
    suspend fun execute(): Flow<List<Meeting>>
}