package ru.lim1x.domain.usecase_implementation.meetings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.interfaces.usecases.IGetMeetingsByCommunityIdUseCase
import ru.lim1x.domain.models.Meeting

internal class GetMeetingsByCommunityIdUseCase(private val meetingRepository: IMeetingsRepository): IGetMeetingsByCommunityIdUseCase {
    override fun execute(communityId: Int): StateFlow<List<Meeting>> {
        return meetingRepository.loadMeetingsByCommunityId(communityId)
    }
}