package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.StateFlow

interface ISetUserVisitingMeetingValueUseCase {
    fun execute(meetingId:Int, userId:Int, isVisiting:Boolean): Boolean
}