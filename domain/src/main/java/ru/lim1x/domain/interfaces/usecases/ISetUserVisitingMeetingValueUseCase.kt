package ru.lim1x.domain.interfaces.usecases

interface ISetUserVisitingMeetingValueUseCase {
    suspend fun execute(meetingId:Int, userId:Int, isVisiting:Boolean):Boolean
}