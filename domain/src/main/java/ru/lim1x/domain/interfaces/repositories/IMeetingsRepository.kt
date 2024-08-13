package ru.lim1x.domain.interfaces.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.models.Meeting
import ru.lim1x.domain.models.MeetingDetailed

interface IMeetingsRepository {
    fun loadAllMeetings(): StateFlow<List<Meeting>>
    fun loadActiveMeetings(): StateFlow<List<Meeting>>
    fun loadPlannedMeetings(userId:Int): StateFlow<List<Meeting>>
    fun loadFinishedMeetings(): StateFlow<List<Meeting>>

    fun loadMeetingsByCommunityId(communityId:Int): StateFlow<List<Meeting>>

    fun loadMeetingDetailed(meetingId:Int): StateFlow<MeetingDetailed?>

    fun removeUserFromVisitingList(meetingId: Int, userId:Int):Boolean

    fun addUserToVisitingList(meetingId: Int, userId:Int):Boolean

    fun getUserAvatar(userId:Int):String?
}