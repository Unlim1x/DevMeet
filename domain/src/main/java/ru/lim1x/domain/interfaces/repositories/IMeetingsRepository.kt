package ru.lim1x.domain.interfaces.repositories

import kotlinx.coroutines.flow.Flow
import ru.lim1x.domain.models.Meeting
import ru.lim1x.domain.models.MeetingDetailed

interface IMeetingsRepository {
    suspend fun loadAllMeetings(): Flow<List<Meeting>>
    suspend fun loadActiveMeetings(): Flow<List<Meeting>>
    suspend fun loadPlannedMeetings(): Flow<List<Meeting>>
    suspend fun loadFinishedMeetings(): Flow<List<Meeting>>

    suspend fun loadMeetingsByCommunityId(communityId:Int): Flow<List<Meeting>>

    suspend fun loadMeetingDetailed(meetingId:Int):Flow<MeetingDetailed>

    suspend fun removeUserFromVisitingList(meetingId: Int, userId:Int):Boolean

    suspend fun addUserToVisitingList(meetingId: Int, userId:Int):Boolean

    suspend fun getUserAvatar(userId:Int):String
}