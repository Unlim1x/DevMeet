package ru.lim1x.domain.interfaces.usecases

interface ISaveUserProfileNameUseCase {
    suspend fun execute(userId:Int, name:String, surname:String = ""):Boolean
}