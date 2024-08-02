package ru.lim1x.domain.usecase_implementation.meetings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.interfaces.usecases.IGetActiveMeetingsUseCase
import ru.lim1x.domain.models.Meeting

internal class GetActiveMeetingsUseCase(private val meetingRepository:IMeetingsRepository) : IGetActiveMeetingsUseCase {
    override fun execute(): StateFlow<List<Meeting>> {
        return meetingRepository.loadActiveMeetings()
    }
}