package ru.lim1x.domain.usecase_implementation.meetings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.interfaces.usecases.IGetMeetingDetailedInfoByIdUseCase
import ru.lim1x.domain.models.IdAndAvatar
import ru.lim1x.domain.models.MeetingDetailed
import ru.lim1x.domain.models.MeetingDetailedExt

internal class GetMeetingDetailedInfoByIdUseCase(private val meetingRepository: IMeetingsRepository): IGetMeetingDetailedInfoByIdUseCase {
    override suspend fun execute(meetingId: Int): MutableStateFlow<MeetingDetailed> {
       return meetingRepository.loadMeetingDetailed(meetingId)
           //.map {
          // it.mapToExt(visitors = it.visitorsIds.map {visitorId-> IdAndAvatar(visitorId, meetingRepository.getUserAvatar(visitorId)) })
       //}
    }
}