package ru.lim1x.domain.interfaces.usecases

interface ISaveNumberUseCase {
    suspend fun execute(phone:String):Boolean
}