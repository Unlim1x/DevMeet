package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.models.MeetingDetailed
import ru.lim1x.domain.models.MeetingDetailedExt

interface IGetMeetingDetailedInfoByIdUseCase {
    fun execute(meetingId:Int): StateFlow<MeetingDetailed?>
}