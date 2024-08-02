package ru.lim1x.domain.usecase_implementation.meetings

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.interfaces.usecases.IGetPlannedMeetingsUseCase
import ru.lim1x.domain.models.Meeting

internal class GetPlannedMeetingsUseCase(private val meetingRepository: IMeetingsRepository): IGetPlannedMeetingsUseCase {
    override suspend fun execute(): Flow<List<Meeting>> {
        return meetingRepository.loadPlannedMeetings()
    }
}