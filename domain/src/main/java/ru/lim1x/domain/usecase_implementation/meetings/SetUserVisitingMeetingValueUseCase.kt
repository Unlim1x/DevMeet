package ru.lim1x.domain.usecase_implementation.meetings

import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.interfaces.usecases.ISetUserVisitingMeetingValueUseCase

internal class SetUserVisitingMeetingValueUseCase(private val meetingRepository: IMeetingsRepository): ISetUserVisitingMeetingValueUseCase {
    override fun execute(meetingId: Int, userId: Int, isVisiting: Boolean): Boolean {
        return if(isVisiting){
            meetingRepository.addUserToVisitingList(meetingId, userId)
        } else{
            meetingRepository.removeUserFromVisitingList(meetingId, userId)
        }
    }
}