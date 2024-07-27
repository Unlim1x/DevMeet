package ru.lim1x.domain.usecase_implementation.meetings

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.interfaces.usecases.IGetMeetingsByCommunityIdUseCase
import ru.lim1x.domain.models.Meeting

internal class GetMeetingsByCommunityIdUseCase(private val meetingRepository: IMeetingsRepository): IGetMeetingsByCommunityIdUseCase {
    override suspend fun execute(communityId: Int): Flow<List<Meeting>> {
        return meetingRepository.loadMeetingsByCommunityId(communityId)
    }
}