package ru.lim1x.repository.meeting_repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.models.Meeting
import ru.lim1x.domain.models.MeetingDetailed
import ru.lim1x.repository.mock_source.MockDataSource

internal class MeetingRepository(private val dataSource:MockDataSource) : IMeetingsRepository {
    override suspend fun loadAllMeetings(): Flow<List<Meeting>> {
        return flowOf(dataSource.getAllMeetings())
    }

    override suspend fun loadActiveMeetings(): Flow<List<Meeting>> {
        return flowOf(dataSource.getActiveMeetings())
    }

    override suspend fun loadPlannedMeetings(): Flow<List<Meeting>> {
        return flowOf(dataSource.getVisitingMeetings())
    }

    override suspend fun loadFinishedMeetings(): Flow<List<Meeting>> {
        return flowOf(dataSource.getFinishedMeetings())
    }

    override suspend fun loadMeetingDetailed(meetingId: Int): MutableStateFlow<MeetingDetailed> {
        return (dataSource.getDetailedMeetingInfo(meetingId=meetingId))
    }

    override suspend fun removeUserFromVisitingList(meetingId: Int, userId: Int): Boolean {
        return dataSource.removeUserFromVisitingList(meetingId)
    }

    override suspend fun addUserToVisitingList(meetingId: Int, userId: Int): Boolean {
        return dataSource.addUserToVisitingList(userId = userId, meetingId = meetingId)
    }

    override suspend fun loadMeetingsByCommunityId(communityId: Int): Flow<List<Meeting>> {
        return flowOf(dataSource.getAllMeetings())
    }

    override fun getUserAvatar(userId: Int): String {
        return dataSource.getVisitorAvatarById(userId)
    }
}