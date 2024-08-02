package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.lim1x.domain.models.MeetingDetailed
import ru.lim1x.domain.models.MeetingDetailedExt

interface IGetMeetingDetailedInfoByIdUseCase {
    suspend fun execute(meetingId:Int): MutableStateFlow<MeetingDetailed>
}