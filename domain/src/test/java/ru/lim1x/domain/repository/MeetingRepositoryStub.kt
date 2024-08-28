package ru.lim1x.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.models.Meeting
import ru.lim1x.domain.models.MeetingDetailed

internal class MeetingRepositoryStub(private val dataSource:DataSourceTest):IMeetingsRepository {
    private val allMeetingsStateFlow: MutableStateFlow<List<Meeting>> =
        MutableStateFlow(emptyList())
    private val activeMeetingsStateFlow: MutableStateFlow<List<Meeting>> =
        MutableStateFlow(emptyList())
    private val plannedMeetingsStateFlow: MutableStateFlow<List<Meeting>> =
        MutableStateFlow(emptyList())
    private val passedMeetingsStateFlow: MutableStateFlow<List<Meeting>> =
        MutableStateFlow(emptyList())
    private val meetingsByCommunity: MutableStateFlow<List<Meeting>> = MutableStateFlow(emptyList())

    override fun loadAllMeetings(): StateFlow<List<Meeting>> {
        allMeetingsStateFlow.update { dataSource.getAllMeetings() }
        return allMeetingsStateFlow
    }

    override fun loadActiveMeetings(): StateFlow<List<Meeting>> {
        activeMeetingsStateFlow.update { dataSource.getActiveMeetings() }
        return activeMeetingsStateFlow
    }

    override fun loadPlannedMeetings(userId: Int): StateFlow<List<Meeting>> {
        plannedMeetingsStateFlow.update { dataSource.getVisitingMeetings() }
        return plannedMeetingsStateFlow
    }

    override fun loadFinishedMeetings(): StateFlow<List<Meeting>> {
        passedMeetingsStateFlow.update { dataSource.getFinishedMeetings() }
        return passedMeetingsStateFlow
    }

    //todo: потом переписать, сейчас датасорс выполняет функцию сервиса и репозиторий - просто прокладка
    override fun loadMeetingDetailed(meetingId: Int): StateFlow<MeetingDetailed> {
        return dataSource.getDetailedMeetingInfo(meetingId = meetingId)
    }

    override fun removeUserFromVisitingList(meetingId: Int, userId: Int): Boolean {
        val done = dataSource.removeUserFromVisitingList(meetingId)
        plannedMeetingsStateFlow.update { dataSource.getVisitingMeetings() }
        return done
    }

    override fun addUserToVisitingList(meetingId: Int, userId: Int): Boolean {
        val done = dataSource.addUserToVisitingList(userId = userId, meetingId = meetingId)
        plannedMeetingsStateFlow.update { dataSource.getVisitingMeetings() }
        return done
    }

    //todo: переписать, пока просто заглушка
    override fun loadMeetingsByCommunityId(communityId: Int): StateFlow<List<Meeting>> {
        meetingsByCommunity.update { dataSource.getAllMeetings() }
        return meetingsByCommunity
    }

    override fun getUserAvatar(userId: Int): String {
        return dataSource.getVisitorAvatarById(userId)
    }
}