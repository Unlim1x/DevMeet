package ru.lim1x.domain.usecase_implementation.meetings

import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.interfaces.usecases.ISetUserVisitingMeetingValueUseCase

internal class SetUserVisitingMeetingValueUseCase(private val meetingRepository: IMeetingsRepository): ISetUserVisitingMeetingValueUseCase {
    override suspend fun execute(meetingId: Int, userId: Int, isVisiting: Boolean): Boolean {
        if(isVisiting){
            return meetingRepository.addUserToVisitingList(meetingId, userId)
        }
        else{
            return meetingRepository.removeUserFromVisitingList(meetingId, userId)
        }
    }
}