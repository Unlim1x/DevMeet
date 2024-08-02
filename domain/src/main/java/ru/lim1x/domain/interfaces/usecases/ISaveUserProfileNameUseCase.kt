package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.StateFlow

interface ISaveUserProfileNameUseCase {
    fun execute(userId:Int, name:String, surname:String = ""): Boolean
}