package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.MeetingDetailedExt

interface IGetMeetingDetailedInfoByIdUseCase {
    suspend fun execute(meetingId:Int): Flow<MeetingDetailedExt>
}