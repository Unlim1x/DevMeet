package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Meeting

interface IGetPlannedMeetingsUseCase {
    //todo: потом передать параметр id встречи, сейчас моки возвращают конкретные списки
    suspend fun execute(): Flow<List<Meeting>>
}