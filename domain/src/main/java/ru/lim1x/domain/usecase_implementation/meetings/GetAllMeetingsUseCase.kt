package ru.lim1x.domain.usecase_implementation.meetings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.interfaces.usecases.IGetAllMeetingsUseCase
import ru.lim1x.domain.models.Meeting

internal class GetAllMeetingsUseCase(private val meetingRepository: IMeetingsRepository): IGetAllMeetingsUseCase {
    override fun execute(): StateFlow<List<Meeting>> {
        return meetingRepository.loadAllMeetings()
    }
}